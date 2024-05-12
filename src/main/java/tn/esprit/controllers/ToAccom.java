package tn.esprit.controllers;

import tn.esprit.entities.Voyage;
import tn.esprit.services.IAccomodation;
import tn.esprit.services.VoyageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ToAccom {
    IAccomodation sv = new IAccomodation();

    @FXML
    public void ToAccmo() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherAccomodation.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Voyage");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ToBooking() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherBooking.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Groupe");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ToAjoutAcc() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterAccomodation.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Groupe");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void ToAjoutBook() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterBooking.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Groupe");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}


