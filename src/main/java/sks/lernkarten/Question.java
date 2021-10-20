package sks.lernkarten;

import com.itextpdf.layout.element.BlockElement;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import java.util.ArrayList;
import java.util.List;

public class Question {

    private int number;
    private Category category;
    private List<BlockElement<?>> content;

    public Question() {
        this.content = new ArrayList<>();
    }

    public Question taskNumber(int number) {
        this.number = number;
        return this;
    }

    public Question category(Category category) {
        this.category = category;
        return this;
    }

    public Question add(BlockElement<?> element) {
        content.add(element);
        return this;
    }

    public void fillQuestionHeader(Paragraph p) {
        p.add(category.getTopic() + "-" + String.format("%03d", this.number));
    }

    public void fillTo(Div p) {
        content.forEach(contentElement -> {
            p.add(contentElement);
        });
    }

}
