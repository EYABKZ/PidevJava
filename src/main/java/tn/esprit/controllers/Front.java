package tn.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Objects;

public class Front {

    @FXML
    private Button book;

    @FXML
    void actionBook(ActionEvent event) {

            try {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Payment.fxml")));
        book.getScene().setRoot(root);
    } catch (
    IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(e.getMessage());
        alert.show();
    }



    }

}
