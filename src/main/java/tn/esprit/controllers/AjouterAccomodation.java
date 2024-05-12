package tn.esprit.controllers;


import javafx.scene.control.Button;
import tn.esprit.entities.Accomodation;
import tn.esprit.services.IAccomodation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;


public class AjouterAccomodation {


    @FXML
    private TextField TFIDA;

    @FXML
    private TextField TFDescriptionA;

    @FXML
    private Button home;

    @FXML
    private TextField TFLieuA;


    private final IAccomodation ia = new IAccomodation();

    @FXML
    public void ActionAjouterAccomodation(ActionEvent event) {


        // Vérifie si les champs sont vides
        if (TFLieuA.getText().isEmpty() || TFDescriptionA.getText().isEmpty()) {
            // Affiche une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode
        }

       try {
           ia.ajouter(new Accomodation(TFIDA.getText() ,TFLieuA.getText() , TFDescriptionA.getText()));
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("GG");
           alert.setContentText("Felicitation ajouté avec SUCCESS");
           alert.show();
       }catch (SQLException e){
           Alert alert = new Alert(Alert.AlertType.ERROR);
           alert.setTitle("GG");
           alert.setContentText(e.getMessage());
           alert.show();
       }
    }




    @FXML
    void actionAfficherAccomodation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherAccomodation.fxml"));
            TFIDA.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    void ActionModifierAccomodation(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/UpdateAccomodation.fxml"));
            TFIDA.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

    @FXML
    void actionDelete(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SupprimerAccomodation.fxml"));
            TFIDA.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }


    @FXML
    void actionHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home.fxml")));
            home.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }
}


