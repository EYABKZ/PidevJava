package tn.esprit.controllers;

import tn.esprit.entities.Voyage;
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
import tn.esprit.controllers.AfficherVoyage;

public class ToVoyage {
    VoyageService sv = new VoyageService();

    @FXML
    public void ToAddAction() {
        Stage primaryStage = new Stage();
        AfficherVoyage a = new AfficherVoyage();
        a.x=1;
        System.out.println("x = 1");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherVoyage.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Voyage");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ToAddActionC() {
        Stage primaryStage = new Stage();
        AfficherVoyage a = new AfficherVoyage();
        a.x=2;
        System.out.println("x = 2");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherGroupe.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Groupe");
            primaryStage.setScene(scene);
            primaryStage.show();

            //AfficherVoyage a = new AfficherVoyage();
            //a.initializeGroupeTable();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}


