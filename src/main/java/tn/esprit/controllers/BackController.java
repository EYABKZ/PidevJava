package tn.esprit.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class BackController implements Initializable {

    @FXML
    private ImageView ImageP;
    @FXML
    private Button Voyage;
    @FXML
    private Button ReserverVoyage;
    @FXML
    private Button Post;
    @FXML
    private Button Reclamation;
    @FXML
    private Button Restaurant;
    @FXML
    private Button ReserverRestaurant;
    @FXML
    private Button Fermer;
    @FXML
    private Button Gestion_User;

    /**
     * Initializes the controller class.
     */
    @FXML
    public void exit(){
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }



    @FXML
    private void Gestion_User(ActionEvent event) {
        try {

              Parent parent = FXMLLoader.load(getClass().getResource("/Admin.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @FXML
    private void Gestion_Transport(ActionEvent event) {
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



}
