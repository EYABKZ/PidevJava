package tn.esprit.controllers;



import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import tn.esprit.services.IBooking;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class DeleteBooking {

    @FXML
    private TextArea TFIDBDel;

    @FXML
    private Button retour;

    private IBooking bookingService;

    public DeleteBooking() {
        bookingService = new IBooking();
    }

    @FXML
    void actionretour(ActionEvent event) {
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

    @FXML
    void actionDelete(ActionEvent event) {
        // Get the ID of the accommodation to delete from the TextArea
        String idString = TFIDBDel.getText().trim();

        // Check if the ID is empty
        if (idString.isEmpty()) {
            // Show an alert if the ID is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty ID");
            alert.setContentText("Please enter the ID of the accommodation to delete.");
            alert.showAndWait();
            return;
        }

        try {
            // Parse the ID to an integer
            int id = Integer.parseInt(idString);
            // Call the service method to delete the accommodation
            bookingService.supprimer(id);

            // Show a success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Accommodation deleted successfully.");
            alert.show();
        } catch (NumberFormatException e) {
            // Show an alert if the ID is not a valid integer
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid ID");
            alert.setContentText("Please enter a valid ID.");
            alert.show();
        } catch (SQLException e) {
            // Show an alert if there is an SQL exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("An error occurred while deleting the accommodation: " + e.getMessage());
            alert.show();
        }
    }
}
