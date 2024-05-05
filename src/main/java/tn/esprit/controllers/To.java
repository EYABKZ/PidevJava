package tn.esprit.controllers;

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
import tn.esprit.entities.Calendar;
import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceCalendar;
import tn.esprit.services.ServiceMoy_Transport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class To {
    ServiceMoy_Transport sv = new ServiceMoy_Transport();
    ServiceCalendar sc = new ServiceCalendar();

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

    public void ToAddActionC() {
        Stage primaryStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCalendarM.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Ajouter Calendar");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ToListAction() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTM.fxml"));
        try {
            Parent root = loader.load();
            AfficherTControllers afficherTControllers = loader.getController();



            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");


            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ToListActionC() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCM.fxml"));
        try {
            Parent root = loader.load();
            AfficherCControllers afficherCControllers = loader.getController();

            // Récupérez tous les événements de calendrier de la base de données
            List<Calendar> allEvents = sc.recuperer();

            // Ajoutez tous les événements à la ListView
            ObservableList<Calendar> items = FXCollections.observableArrayList(allEvents);
            afficherCControllers.AfficherList.setItems(items);

            // Utilisez une CellFactory pour afficher le titre de l'événement comme le texte des éléments de la liste
            afficherCControllers.AfficherList.setCellFactory(param -> new ListCell<Calendar>() {
                @Override
                protected void updateItem(Calendar item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getTitle() == null) {
                        setText(null);
                    } else {
                        setText(item.getTitle());
                    }
                }
            });

            // Définissez un écouteur de clic pour les éléments de la ListView
            afficherCControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Récupérez les détails de l'événement sélectionné
                Calendar selectedEvent = newValue;

                // Affichez les détails de l'événement sélectionné
                afficherCControllers.setTitle(selectedEvent.getTitle());
                afficherCControllers.setStart(selectedEvent.getStart());
                afficherCControllers.setEnd(selectedEvent.getEnd());
                afficherCControllers.setDescription(selectedEvent.getDescription());
                afficherCControllers.setAll_Day(String.valueOf(selectedEvent.getAll_Day()));
                afficherCControllers.setBackgroundColor(selectedEvent.getBackground_Color());
                afficherCControllers.setBorderColor(selectedEvent.getBorder_Color());
                afficherCControllers.setTextColor(selectedEvent.getText_Color());
                afficherCControllers.setTransport_Model(selectedEvent.getId_transport());
                afficherCControllers.setPassenger_Count(String.valueOf(selectedEvent.getPassenger_Count()));
                afficherCControllers.setId(String.valueOf(selectedEvent.getId()));
            });

            // Déclaration de débogage pour vérifier si le FXML est chargé avec succès
            System.out.println("FXML loaded successfully.");
            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/Admin/IdeaProjects/ProjetPidev/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | SQLException e) {
            // Au lieu de simplement imprimer, gérez l'IOException
            e.printStackTrace();
        }
    }

}


