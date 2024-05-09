package tn.esprit.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tn.esprit.entities.Accomodation;
import tn.esprit.services.IAccomodation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UpdateAccomodation {

    @FXML
    private TextField TFIDA;

    @FXML
    private Button retour;

    @FXML
    private TextField TFDescriptionA;

    @FXML
    private TextField TFLieuA;

    private IAccomodation ia = new IAccomodation();

    @FXML
    void update(ActionEvent event) {
        // Check if any of the fields are empty
        if (TFIDA.getText().isEmpty() || TFLieuA.getText().isEmpty() || TFDescriptionA.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            // Get ID as String from TextField
            String id = TFIDA.getText();
            // Create Accomodation object with ID, lieu, and description
            Accomodation updatedAccomodation = new Accomodation(id, TFLieuA.getText(), TFDescriptionA.getText());
            // Call modifier method with the updated Accomodation object
            ia.modifier(updatedAccomodation);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Accomodation updated successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @FXML
    void actionretour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterAccomodation.fxml")));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
