/*package tn.esprit.controllers;


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

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class AjouterAccomodation {


    @FXML
    private TextField TFIDA;

    @FXML
    private TextField TFDescriptionA;

    @FXML
    private TextField TFLieuA;

    @FXML
    void stat(ActionEvent event) {

    }

    private final IAccomodation ia = new IAccomodation();

    @FXML
    public void ActionAjouterAccomodation(ActionEvent event) {

        String id = TFIDA.getText();
        String lieu = TFLieuA.getText();
        String description = TFDescriptionA.getText();

        // Vérifie si les champs sont vides
        if (id.isEmpty() || lieu.isEmpty() || description.isEmpty()) {
            // Affiche une alerte si un champ est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode
        }

        // Tous les champs sont remplis, procédez à l'ajout de l'événement
        Accomodation accomodation = new Accomodation(id,lieu, description);
        IAccomodation IAccomodation = new MyIAccomodation();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAccomodation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AjouterAccomodation AjouterAccomodation = loader.getController();
            IAccomodation.ajouter(accomodation);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Accomodation ajouté avec succès");
            alert.show();
        /*
            // Pass any necessary data to the new interface

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

             //Close the current window
            root.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }*/
            //btn1.getScene().getWindow().hide();
      //  } catch (IOException e) {
      /*      e.printStackTrace(); // Handle the exception as needed
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAccomodation.fxml"));
            Parent root = loader.load();

            // Access the controller of the new interface
            AjouterAccomodation AjouterAccomodation = loader.getController();


            // Pass any necessary data to the new interface


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Close the current window

            root.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }


    private static class MyIAccomodation extends IAccomodation {
        @Override
        public void supprimer(int id) throws SQLException {

        }

        @Override
        public void modifier(Accomodation accomodation) throws SQLException {

        }

        @Override
        public List<Accomodation> lister() throws SQLException {
            return List.of();
        }



        @Override
        public void ajouter(Accomodation accomodation) throws SQLException {

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
}


*/