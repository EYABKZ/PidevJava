package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceMoy_Transport;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherTControllers {

    public ListView <Moy_Transport> AfficherList;
    @FXML
    private TextField txtId;

    @FXML
    private ImageView Transport_Picture = new ImageView();

    @FXML
    private TextField txtTransport_Model;

    @FXML
    private TextField txtTransport_Price;

    @FXML
    private TextField txtTransport_Description;

    @FXML
    private TextField txtDisponibility;

    public void setTransport_Picture(String Transport_Picture) {
        Image image = new Image(Transport_Picture); // Create an Image from the URL
        this.Transport_Picture.setImage(image); // Set the Image to the ImageView
    }

    public void settxtTransport_Model(String txtTransport_Model) {
        this.txtTransport_Model.setText(txtTransport_Model);
    }

    public void settxtTransport_Price(String txtTransport_Price) {
        this.txtTransport_Price.setText(txtTransport_Price);
    }

    public void settxtTransport_Description(String txtTransport_Description) {
        this.txtTransport_Description.setText(txtTransport_Description);
    }

    public void settxtDisponibility(String TxtDisponibility) {
        this.txtDisponibility.setText(TxtDisponibility);
    }

    public void settxtId(String txtId) {
        this.txtId.setText(txtId);
    }

    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierT.fxml"));

        try {
            Parent root = loader.load();
            ModifierTransportController modifierController = loader.getController();
            modifierController.setTxtTransport_Description(txtTransport_Description.getText());
            modifierController.setTxtTransport_Model(txtTransport_Model.getText());
            modifierController.setTxtTransport_Price(txtTransport_Price.getText());
            String imageUrl = Transport_Picture.getImage().getUrl();
            modifierController.setTransport_Picture(imageUrl);
            modifierController.setTxtDisponibility(txtDisponibility.getText());
            modifierController.setTxtId(txtId.getText());

            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

}