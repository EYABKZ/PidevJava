package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import tn.esprit.entities.Accomodation;
import tn.esprit.entities.Booking;
import tn.esprit.services.IBooking;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;



public class AfficherBooking implements Initializable {

    @FXML
    private TextField searchfield;

    @FXML
    private Button add;

    @FXML
    private TableView<Booking> TVB;

    @FXML
    private Button edit;

    @FXML
    private Button home;

    @FXML
    private Button refresh;

    @FXML
    private Button delete;

    private final IBooking iBooking = new IBooking();

    @FXML
    private HBox cartev;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Booking> bookings = iBooking.lister();
            for (Booking booking : bookings) {
                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteBooking.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carteBooking carte = carteArticleLoader.getController();

                carte.setId(String.valueOf(booking.getId()));
                carte.setDebut((Date) booking.getDebut());
                carte.setFin((Date) booking.getFin());
                carte.setAccomodationId(String.valueOf(booking.getAccomodationId()));
                carte.setUserId(String.valueOf(booking.getUserId()));

                cartev.getChildren().add(carteArticleBox);
            }
        } catch (IOException e) {
            System.err.println("Error loading carteBooking.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void actionRefresh(ActionEvent event) {
        refreshTable();
    }

    @FXML
    void actionAddBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterBooking.fxml")));
            add.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }    }

    @FXML
    void actionUpdateBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UpdateBooking.fxml")));
            edit.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void actionDeleteBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DeleteBooking.fxml"));
            delete.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void refreshTable() {
        try {
            ArrayList<Booking> bookings = iBooking.lister();
            TVB.getItems().clear();
            TVB.getItems().addAll(bookings);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading bookings: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void actionHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home.fxml")));
            home.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }


    @FXML
    void sffff(KeyEvent event) throws SQLException, IOException {
        List<Booking> bookings = iBooking.search(Date.valueOf(searchfield.getText()));
//        System.out.println(bookings);
//        cartev.getChildren().clear();
        for (Booking booking : bookings) {
            FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteBooking.fxml"));
            HBox carteArticleBox = carteArticleLoader.load();
            carteBooking carte = carteArticleLoader.getController();

            carte.setId(String.valueOf(booking.getId()));
            carte.setDebut((Date) booking.getDebut());
            carte.setFin ((Date) booking.getFin());

            cartev.getChildren().add(carteArticleBox);
        }
    }

    public class PDFGenerator {

        public void generatePDF(List<Booking> bookings, String filePath) throws IOException {
            try (PDDocument document = new PDDocument()) {
                PDPage coverPage = createCoverPage(document);
                document.addPage(coverPage);

                PDPage contentPage = new PDPage();
                document.addPage(contentPage);

                addContent(document, bookings, contentPage);

                document.save(filePath);
            }
        }

        private PDPage createCoverPage(PDDocument document) throws IOException {
            PDPage page = new PDPage();
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Bookings Report");
                contentStream.endText();
            }
            return page;
        }

        private void addContent(PDDocument document, List<Booking> bookings, PDPage page) throws IOException {
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                int yOffset = 680;
                for (Booking booking : bookings) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(100, yOffset);
                    contentStream.showText("ID: " + booking.getId()+"      Debut: " + booking.getDebut()+"    Fin: " + booking.getFin()+"   User ID: " + booking.getUserId()+"     Accomodation Id: " + booking.getAccomodationId());
                    contentStream.newLine();
//                    contentStream.showText("Debut: " + booking.getDebut());
//                    contentStream.newLine();
//                    contentStream.showText("Fin: " + booking.getFin());
//                    contentStream.newLine();
//                    contentStream.showText("User ID: " + booking.getUserId());
//                    contentStream.newLine();
//                    contentStream.showText("Accomodation Id: " + booking.getAccomodationId());
//                    contentStream.newLine();
                    contentStream.endText();
                    yOffset -= 50; // Adjust the vertical position for the next booking
                }
            }
        }

    }
    @FXML
    void handlePrintButton(ActionEvent event) {
        try {
            // Get the list of bookings
            List<Booking> bookings = iBooking.lister();

            // Generate PDF
            AfficherBooking.PDFGenerator pdfGenerator = new AfficherBooking.PDFGenerator();
            pdfGenerator.generatePDF(bookings, "bookings.pdf");

            // Display success message
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("PDF Generated");
            successAlert.setHeaderText(null);
            successAlert.setContentText("PDF generated successfully.");
            successAlert.showAndWait();
        } catch (Exception e) {
            // Handle any exceptions that occur during PDF generation
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Error generating PDF: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
}
