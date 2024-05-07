package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import java.util.ArrayList;
import java.util.List;

public class AjouterTransportController {

    ServiceMoy_Transport sv = new ServiceMoy_Transport();

    @FXML
    Label label = new Label();
    private ImageView Transport_Picture = new ImageView();

    @FXML
    private  Button button = new Button("Open a file");

    @FXML
    private TextField txtTransport_Model;

    @FXML
    private TextField txtTransport_Price;

    @FXML
    private TextField txtTransport_Description;

    @FXML
    private TextField txtDisponibility;

    @FXML
    void button(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a file");
        File initialDirectory = new File(System.getProperty("user.home")+ "/Desktop");
        if (!initialDirectory.exists() || !initialDirectory.isDirectory()) {
            initialDirectory = new File(System.getProperty("user.home"));
        }
        fileChooser.setInitialDirectory(initialDirectory);

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPEG Image","*.jpg"), new FileChooser.ExtensionFilter("PNG Image", "*.png"), new FileChooser.ExtensionFilter("All image files","*.jpg","*.png"));
        Stage stage = (Stage) button.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            label.setText(selectedFile.getName());
            String imageUrl = "file:" + selectedFile.getAbsolutePath();
            Image image = new Image(imageUrl);
            Transport_Picture.setImage(image);
        }
    }

    @FXML
    void ajouterAction(ActionEvent event) {
        try {
            String imagePath = Transport_Picture.getImage().getUrl();
            String model = txtTransport_Model.getText();
            String price = txtTransport_Price.getText();
            String description = txtTransport_Description.getText();
            String disponibility = txtDisponibility.getText();


            if (!price.matches("\\d+") || price.isEmpty()) {
                showAlert("Invalid input: txtTransport_Price should only contain integers and should not be empty.");
                return;
            }
            if (!description.matches("[a-zA-Z]+") || description.isEmpty()) {
                showAlert("Invalid input: txtTransport_Description should only contain characters and should not be empty.");
                return;
            }
            if (!disponibility.matches("[a-zA-Z]+") || disponibility.isEmpty()) {
                showAlert("Invalid input: txtDisponibility should only contain characters and should not be empty.");
                return;
            }

            Moy_Transport T= new Moy_Transport(imagePath, model, Integer.parseInt(price), description, disponibility);
            int generatedId = sv.ajouter(T);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
             alert.setTitle("Succes");
            alert.setContentText("Transport Ajouter avec succes");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTM.fxml"));

            try {
                Parent root = loader.load();
                AfficherTControllers afficherTControllers = loader.getController();



                txtTransport_Description.getScene().setRoot(root);
            } catch (IOException e) {
                // Instead of just printing, handle the IOException
                e.printStackTrace();
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }

    void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setTransport_Picture(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image(imagePath);
            this.Transport_Picture.setImage(image);
        } else {
            // Handle the case where the imagePath is empty or null
            System.out.println("Image path is empty or null");
        }
    }

    public void setTxtTransport_Model(String txtTransport_Model) { this.txtTransport_Model.setText(txtTransport_Model);}

    public void setTxtTransport_Price(String txtTransport_Price) { this.txtTransport_Price.setText(txtTransport_Price);}

    public void setTxtTransport_Description(String txtTransport_Description) {this.txtTransport_Description.setText(txtTransport_Description);}

    public void setTxtDisponibility(String txtDisponibility) { this.txtDisponibility.setText(txtDisponibility);}
}
