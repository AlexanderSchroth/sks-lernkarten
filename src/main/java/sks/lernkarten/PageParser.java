package sks.lernkarten;

import java.util.List;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class PageParser {
    static final String ELWIS_HOST = "https://www.elwis.de";

    private TaskProducer taskProducer;

    public PageParser(TaskProducer taskProducer) {
        this.taskProducer = taskProducer;
    }

    void parse(Category c) throws Exception {
        Document doc = Jsoup.connect(c.getUrl()).get();
        System.out.println(doc.title());
        Element content = doc.select("#content").first();
        Elements childNodes = content.children();

        for (Element element : childNodes) {
            if (element.text().matches("Nummer .*")) {
                String numberStr = element.text().replaceAll("Nummer ", "").replaceAll(":", "");
                taskProducer.taskNumber(c, Integer.parseInt(numberStr));
                System.out.println("found number " + element.toString());
            } else if (element.normalName().equals("p")) {
                if (element.hasClass("picture")) {
                    String imgSrc = element.getElementsByTag("img").first().attr("src");
                    taskProducer.picture(ELWIS_HOST + imgSrc);
                    System.out.println("found an question picture " + element.toString());
                } else if (element.children().first() != null && element.children().first().normalName().equals("strong")) {

                    Element strongElement = element.children().first();
                    List<Node> strongElementChildren = strongElement.childNodes();
                    for (Node node : strongElementChildren) {
                        if (node instanceof TextNode) {
                            taskProducer.questionText(((TextNode) node).text());
                        } else {
                            System.out.println("Unkown element: " + node);
                        }
                    }

                    System.out.println("question " + element.toString());
                } else {
                    taskProducer.answer(element.text());
                    System.out.println("found answer " + element.toString());
                }
            } else if (element.normalName().equals("ol") || element.normalName().equals("ul")) {
                List<String> listItemes = element.children().stream().map(li -> li.text()).collect(Collectors.toList());
                taskProducer.answerList(listItemes);
                System.out.println("found answer list " + element.toString());
            } else if (element.normalName().equals("div") && element.hasClass("line")) {
                taskProducer.nextTask();
                System.out.println("found line seperator to next question");
            } else if (element.normalName().equals("strong")) {
                System.out.println("ignoring: " + element.toString());
            } else {
                System.out.println("Unkown element: " + element.toString());
            }
        }
    }
}
