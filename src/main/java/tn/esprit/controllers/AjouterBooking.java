package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import tn.esprit.entities.Booking;
import tn.esprit.services.IBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

public class AjouterBooking {

    @FXML
    private Button retour;
    @FXML
    private DatePicker TFDB;

    @FXML
    private DatePicker TFFB;

    private final IBooking iBooking = new IBooking();

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherBooking.fxml")));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void ajouterReservation(ActionEvent event) {
        if (TFFB.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Field");
            alert.setHeaderText(null);
            alert.setContentText("Please select a date.");
            alert.showAndWait();
            return;
        }

        try {
            Date debut = java.sql.Date.valueOf(TFDB.getValue());
            Date fin = java.sql.Date.valueOf(TFFB.getValue());
// No need to provide values for autoincrement fields user_id and accomodation_id
            Booking booking = new Booking(debut, fin, 0, 0); // Pass 0 for autoincremented fields
            iBooking.ajouter(booking);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Booking added successfully");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
}
