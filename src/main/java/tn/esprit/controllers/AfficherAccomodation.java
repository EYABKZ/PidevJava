package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import tn.esprit.entities.Accomodation;

import tn.esprit.services.IAccomodation;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherAccomodation implements Initializable {


    @FXML
    private HBox cartev;
    
    @FXML
    private Label TFAccomodation;

    @FXML
    private Button retour;

    private final IAccomodation ia = new IAccomodation() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Accomodation> accomodations = ia.lister();
            for (Accomodation accomodation : accomodations) {
                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteAccomodation.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carteAccomodation carte = carteArticleLoader.getController();

                carte.setIdACC(String.valueOf(accomodation.getidaccomodation()));
                carte.setAccomodationLieu( accomodation.getlieuaccomodation());
                carte.setAccomodationdesc ( accomodation.getdescriptionaccomodation());

                cartev.getChildren().add(carteArticleBox);
            }
        } catch (IOException e) {
            System.err.println("Error loading carteAccomodation.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void actionRetour(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterAccomodation.fxml")));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }


}
