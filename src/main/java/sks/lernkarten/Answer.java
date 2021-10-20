package sks.lernkarten;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import java.util.ArrayList;
import java.util.List;

public class Answer {

    private int number;
    private Category category;
    private List<BlockElement<?>> content;

    public Answer() {
        this.content = new ArrayList<>();
    }

    public Answer taskNumber(int number) {
        this.number = number;
        return this;
    }

    public Answer category(Category category) {
        this.category = category;
        return this;
    }

    public Answer add(BlockElement<?> element) {
        content.add(element);
        return this;
    }

    public void fillQuestionHeader(Paragraph p) {
        p.add("Antwort: " + category.getTopic() + "-" + String.format("%03d", this.number));
    }

    public void fillTo(Div p) {
        content.forEach(contentElement -> {
            p.add(contentElement);
            // p.add(new Text("\n"));
        });
    }

}
