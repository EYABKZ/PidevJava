package tn.esprit.entities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class Accomodation {

        private String idaccomodation;
        private String lieuaccomodation;
        private String descriptionaccomodation;

        
        public Accomodation(String idaccomodation, String lieuaccomodation , String descriptionaccomodation) {
            this.idaccomodation = idaccomodation;
            this.lieuaccomodation = lieuaccomodation;
            this.descriptionaccomodation = descriptionaccomodation;
        }

        public Accomodation(String lieuaccomodation, String descriptionaccomodation) {
            this.lieuaccomodation = lieuaccomodation;
            this.descriptionaccomodation=descriptionaccomodation;

        }

        public Accomodation(){}

        public String getidaccomodation() {
            return idaccomodation;
        }

        public String getlieuaccomodation () {
            return lieuaccomodation;
        }

        public String getdescriptionaccomodation () {
            return descriptionaccomodation;
        }


        public void setidaccomodation(String idaccomodation) {
            this.idaccomodation = idaccomodation;
        }

        public void setlieuaccomodation(String lieuaccomodation) {
            this.lieuaccomodation = lieuaccomodation;
        }

        public void setdescriptionaccomodation(String descriptionaccomodation) {
            this.descriptionaccomodation = descriptionaccomodation;
        }
    public static class PDFGenerator {

        public void generatePDF(List<Accomodation> accomodations, String filePath) throws IOException {
            try (PDDocument document = new PDDocument()) {
                PDPage coverPage = createCoverPage(document);
                document.addPage(coverPage);

                PDPage contentPage = new PDPage();
                document.addPage(contentPage);

                addContent(document, accomodations, contentPage);

                document.save(filePath);
            }
        }

        private PDPage createCoverPage(PDDocument document) throws IOException {
            PDPage page = new PDPage();
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Accommodations Report");
                contentStream.endText();
            }
            return page;
        }

        private void addContent(PDDocument document, List<Accomodation> accommodations, PDPage page) throws IOException {
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                int yOffset = 680;
                for (Accomodation accommodation : accommodations) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yOffset);
                    contentStream.showText("ID: " + accommodation.getidaccomodation()+"     Location: " + accommodation.getlieuaccomodation()+"    Description: " + accommodation.getdescriptionaccomodation());
                    contentStream.newLine();
//                    contentStream.showText("Location: " + accommodation.getlieuaccomodation());
//                    contentStream.newLine();
//                    contentStream.showText("Description: " + accommodation.getdescriptionaccomodation());
//                    contentStream.newLine();
                    contentStream.endText();
                    yOffset -= 50; // Adjust the vertical position for the next accommodation
                }
            }
        }

    }


        @Override
        public String toString() {
            return "\n accomodation\n{  " +
                    "id=" + idaccomodation +
                    ", lieu='" + lieuaccomodation + '\'' +
                    ", description='" + descriptionaccomodation + '\'' +
                    "  }\n";
        }


    }


