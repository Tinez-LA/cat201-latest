import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TxtToPdfConverter {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: TxtToPdfConverter <input.txt> <output.pdf>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page);
                 BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {

                contents.beginText();
                contents.setFont(PDType1Font.TIMES_ROMAN, 12);
                contents.setLeading(14.5f);
                contents.newLineAtOffset(25, 700);

                String line;
                while ((line = reader.readLine()) != null) {
                    contents.showText(line);
                    contents.newLine();
                }
                contents.endText();
            }

            doc.save(outputFilePath);
            System.out.println("Conversion completed: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
