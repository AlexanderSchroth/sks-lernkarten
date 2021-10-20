package sks.lernkarten;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.ListNumberingType;
import com.itextpdf.layout.property.TextAlignment;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

class TaskProducer {

    private List<Card> tasks;

    private Question question;
    private Answer answer;

    public TaskProducer() {
        tasks = new ArrayList<>();
    }

    public void taskNumber(Category c, int taskNumber) {
        question = new Question();
        question.taskNumber(taskNumber);
        question.category(c);

        answer = new Answer();
        answer.taskNumber(taskNumber);
        answer.category(c);
    }

    public void nextTask() {
        tasks.add(new Card(question, answer));
    }

    public void picture(String imgSrc) {
        try {
            Paragraph paragraph = new Paragraph();
            Image image = new Image(ImageDataFactory.create(new URL(imgSrc)));
            image.setWidth(100);
            paragraph.add(image);
            question.add(paragraph);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void questionText(String text) {
        text = text.trim();
        if (StringUtils.isNotEmpty(text)) {
            question.add(new Paragraph(text));
        }
    }

    public void answer(String text) {
        text = text.trim();
        if (StringUtils.isNotEmpty(text)) {
            answer.add(new Paragraph(text));
        }
    }

    public void answerList(List<String> listItemes) {
        com.itextpdf.layout.element.List list = new com.itextpdf.layout.element.List(ListNumberingType.DECIMAL);
        list.setTextAlignment(TextAlignment.LEFT);
        listItemes.forEach(item -> list.add(item));
        answer.add(list);
    }

    public List<Card> tasks() {
        return tasks;
    }
}
