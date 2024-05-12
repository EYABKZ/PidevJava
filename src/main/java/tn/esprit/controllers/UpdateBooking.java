package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import tn.esprit.entities.Booking;
import tn.esprit.services.IBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class UpdateBooking {

    @FXML
    private TextField TFIDB;


    @FXML
    private Button retour;

    @FXML
    private DatePicker TFDB;

    @FXML
    private DatePicker TFFB;

    private IBooking ib = new IBooking();

    @FXML
    void update(ActionEvent event) {
        // Check if any of the fields are empty
        if (TFIDB.getText().isEmpty() || TFDB.getValue() == null || TFFB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Fields");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            // Create Booking object with selected dates
            int id = Integer.parseInt(TFIDB.getText());
            Date debut = java.sql.Date.valueOf(TFDB.getValue());
            Date fin = java.sql.Date.valueOf(TFFB.getValue());
            Booking updatedBooking = new Booking(id,debut, fin);
            // Call modifier method with the updated Booking object
            ib.modifier(updatedBooking);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Booking updated successfully.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherBooking.fxml")));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

}
