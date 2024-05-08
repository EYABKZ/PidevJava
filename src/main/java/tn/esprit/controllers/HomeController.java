package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private void goToTransport(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Design.fxml"));

            Scene scene = new Scene(parent);
            scene.getStylesheets().add("file:///C:/Users/yassi/IdeaProjects/projet2/src/main/java/tn/esprit/css/fullpackstyling.css");

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void goToForum(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/AfficherPost.fxml"));

            Scene scene = new Scene(parent);
            //scene.getStylesheets().add("file:///C:/Users/yassi/IdeaProjects/projet2/src/main/java/tn/esprit/css/fullpackstyling.css");

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
