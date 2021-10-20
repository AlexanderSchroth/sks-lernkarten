package sks.lernkarten;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        Main main = new Main();
        main.generatePdf(Category.Navigation);
        main.generatePdf(Category.Schifffahrtsrecht);
        main.generatePdf(Category.Wetterkunde);
        main.generatePdf(Category.Seemannschaft_I);
    }

    public void generatePdf(Category cat) throws Exception {
        TaskProducer taskProducer = new TaskProducer();
        new PageParser(taskProducer).parse(cat);
        List<Card> tasks = taskProducer.tasks();
        List<List<Card>> batch = batch(tasks, 4);
        PdfOutput output = new PdfOutput(new FileOutputStream(cat.getResultFileName()));

        for (List<Card> tasksOnOnePage : batch) {
            if (tasksOnOnePage.size() == 4) {
                Card first = tasksOnOnePage.get(0);
                Card second = tasksOnOnePage.get(1);
                Card third = tasksOnOnePage.get(2);
                Card fourth = tasksOnOnePage.get(3);

                output.add(first.question).nextPage();
                output.add(second.question).nextPage();

                output.add(third.question).nextPage();
                output.add(fourth.question).nextPage();

                output.add(second.answer).nextPage();
                output.add(first.answer).nextPage();

                output.add(fourth.answer).nextPage();
                output.add(third.answer).nextPage();
            } else if (tasksOnOnePage.size() == 3) {
                Card first = tasksOnOnePage.get(0);
                Card second = tasksOnOnePage.get(1);
                Card third = tasksOnOnePage.get(2);

                output.add(first.question).nextPage();
                output.add(second.question).nextPage();

                output.add(third.question).nextPage();
                output.nextPage();

                output.add(second.answer).nextPage();
                output.add(first.answer).nextPage();

                output.nextPage();
                output.add(third.answer).nextPage();
            } else if (tasksOnOnePage.size() == 2) {
                Card first = tasksOnOnePage.get(0);
                Card second = tasksOnOnePage.get(1);

                output.add(first.question).nextPage();
                output.add(second.question).nextPage();

                output.nextPage();
                output.nextPage();

                output.add(second.answer).nextPage();
                output.add(first.answer).nextPage();

                output.nextPage();
                output.nextPage();
            } else if (tasksOnOnePage.size() == 1) {
                Card first = tasksOnOnePage.get(0);

                output.add(first.question).nextPage();
                output.nextPage();

                output.nextPage();
                output.nextPage();

                output.nextPage();
                output.add(first.answer).nextPage();

                output.nextPage();
                output.nextPage();
            }
        }

        output.close();
    }

    private static List<List<Card>> batch(List<Card> fullList, int batchSize) {
        ArrayDeque<Card> deque = new ArrayDeque<>(fullList);
        ArrayList<List<Card>> batches = new ArrayList<>();
        while (!deque.isEmpty()) {
            ArrayList<Card> batch = new ArrayList<>();
            while (!deque.isEmpty() && batch.size() < batchSize) {
                batch.add(deque.pop());
            }
            batches.add(batch);
        }
        return batches;
    }

    private static class PdfOutput {

        private Document document;

        public PdfOutput(OutputStream os) {
            PdfWriter writer = new PdfWriter(os);
            PdfDocument pdf = new PdfDocument(writer);

            this.document = new Document(pdf, PageSize.A6.rotate());
            this.document.setMargins(5, 5, 5, 5);
        }

        public PdfOutput nextPage() {
            document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
            return this;
        }

        public PdfOutput close() {
            document.close();
            return this;
        }

        PdfOutput add(Question q) {
            PdfFont font;
            try {
                font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            Paragraph questionNumber = new Paragraph()
                .setFont(font).setFontSize(10f);
            questionNumber.setTextAlignment(TextAlignment.RIGHT);
            q.fillQuestionHeader(questionNumber);
            document.add(questionNumber);

            Div paragraph = new Div()
                .setFont(font)
                .setFontSize(14f)
                .setPadding(20f)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHeight(PageSize.A6.rotate().getHeight() - 80);

            q.fillTo(paragraph);

            document.add(paragraph);
            return this;
        }

        PdfOutput add(Answer a) {
            PdfFont font;
            try {
                font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }

            Paragraph questionNumber = new Paragraph()
                .setFont(font).setFontSize(10f);
            questionNumber.setTextAlignment(TextAlignment.RIGHT);
            a.fillQuestionHeader(questionNumber);
            document.add(questionNumber);

            Div paragraph = new Div()
                .setFont(font)
                .setFontSize(14f)
                .setPadding(20f)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHeight(PageSize.A6.rotate().getHeight() - 80);
            a.fillTo(paragraph);

            document.add(paragraph);

            return this;
        }
    }
}
