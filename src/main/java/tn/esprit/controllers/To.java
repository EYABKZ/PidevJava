package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceMoy_Transport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class To {

    @FXML
    public void ToAddAction() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterTransportM.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Ajouter Calendar");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}


