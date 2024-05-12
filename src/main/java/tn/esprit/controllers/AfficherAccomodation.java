package tn.esprit.controllers;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.Accomodation;

import tn.esprit.services.IAccomodation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherAccomodation implements Initializable {
    @FXML
    private TextField searchfield;

    @FXML
    private HBox cartev;
    
    @FXML
    private Label TFAccomodation;

    @FXML
    private Button retour;

    private final IAccomodation ia = new IAccomodation() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Accomodation> accomodations = ia.lister_acc();
            for (Accomodation accomodation : accomodations) {
                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteAccomodation.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carteAccomodation carte = carteArticleLoader.getController();

                carte.setIdACC(String.valueOf(accomodation.getidaccomodation()));
                carte.setAccomodationLieu( accomodation.getlieuaccomodation());
                carte.setAccomodationdesc ( accomodation.getdescriptionaccomodation());

                cartev.getChildren().add(carteArticleBox);
            }
        } catch (IOException e) {
            System.err.println("Error loading carteAccomodation.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void actionRetour(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterAccomodation.fxml")));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    void handlePrintButton(ActionEvent event) {
        try {
            // Get the list of accomodations
            List<Accomodation> accomodations = ia.lister_acc();

            // Generate PDF
            Accomodation.PDFGenerator pdfGenerator = new Accomodation.PDFGenerator();
            pdfGenerator.generatePDF(accomodations, "accomodations.pdf");

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


    @FXML
    void ffff(KeyEvent event) throws SQLException, IOException {
        List<Accomodation> accomodations = ia.rech(searchfield.getText());
//        System.out.println(accomodations);
//        cartev.getChildren().clear();
        for (Accomodation accomodation : accomodations) {
            FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteAccomodation.fxml"));
            HBox carteArticleBox = carteArticleLoader.load();
            carteAccomodation carte = carteArticleLoader.getController();

            carte.setIdACC(String.valueOf(accomodation.getidaccomodation()));
            carte.setAccomodationLieu( accomodation.getlieuaccomodation());
            carte.setAccomodationdesc ( accomodation.getdescriptionaccomodation());

            cartev.getChildren().add(carteArticleBox);
    }
    }


}
