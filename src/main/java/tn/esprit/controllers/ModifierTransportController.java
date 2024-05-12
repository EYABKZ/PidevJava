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

public class ModifierTransportController {

    ServiceMoy_Transport sv = new ServiceMoy_Transport();

    private Moy_Transport transportToUpdate;

    @FXML
    private TextField txtId;

    @FXML
    Label label = new Label();
    private ImageView Transport_Picture = new ImageView();

    @FXML
    private Button button = new Button("Open a file");

    @FXML
    private TextField txtTransport_Model;

    @FXML
    private TextField txtTransport_Price;

    @FXML
    private TextField txtTransport_Description;

    @FXML
    private TextField txtDisponibility;

    @FXML
    void ButtonAction(ActionEvent e) {
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
            String fileName = selectedFile.getName(); // Get the file name
            label.setText(fileName);
        }
    }

    public void setTransport_Picture(String Transport_Picture) {
        Image image = new Image(Transport_Picture); // Create an Image from the URL
        this.Transport_Picture.setImage(image); // Set the Image to the ImageView
    }

    public void setTxtTransport_Model(String txtTransport_Model) {
        this.txtTransport_Model.setText(txtTransport_Model);
    }

    public void setTxtTransport_Price(String txtTransport_Price) { this.txtTransport_Price.setText(txtTransport_Price);}

    public void setTxtTransport_Description(String txtTransport_Description) {
        this.txtTransport_Description.setText(txtTransport_Description);
    }

    public void setTxtDisponibility(String txtDisponibility) {
        this.txtDisponibility.setText(txtDisponibility);
    }

    public void setTxtId(String txtId) { this.txtId.setText(txtId); }


    @FXML
    void ModifierAction() {
        try {
            Moy_Transport existingTransport = new Moy_Transport();

            String imageUrl = label.getText();
            existingTransport.setTransport_Picture(imageUrl);
            existingTransport.setTransport_Model(txtTransport_Model.getText());
            existingTransport.setTransport_Price(Integer.parseInt(txtTransport_Price.getText()));
            existingTransport.setTransport_Description(txtTransport_Description.getText());
            existingTransport.setDisponibility(txtDisponibility.getText());
            existingTransport.setId_transport(Integer.parseInt(txtId.getText()));

            // Call the modifier method with the modified Transport object
            sv.modifier(existingTransport);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Transport modifiée avec succès");
            alert.showAndWait();

            // Reload the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherTM.fxml"));
            Parent root = loader.load();

            // Get the current stage from the event source
            Stage stage = (Stage) txtId.getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            // Handle IO exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur lors du chargement de la vue : " + e.getMessage());
            alert.showAndWait();
        } catch (SQLException e) {
            // Handle SQL exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Erreur SQL : " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void SupprimerAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this Transport?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Get the Transport ID
                int transportId = Integer.parseInt(txtId.getText());

                // Delete or update the referencing records first
                // This depends on your database schema and requirements
                // sv.deleteOrUpdateReferencingRecords(transportId);

                // Then delete the Transport record
                sv.supprimer(transportId);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("Transport supprimé avec succès");
                alert.show();

                // Reload the view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTransportM.fxml"));
                try {
                    Parent root = loader.load();

                    AjouterTransportController a = loader.getController();
                    a.setTxtTransport_Description("");
                    a.setTxtTransport_Price("");
                    a.setTxtTransport_Model("");
                    a.setTxtDisponibility("");
                    String imageUrl = Transport_Picture.getImage().getUrl();
                    a.setTransport_Picture(imageUrl);

                    txtTransport_Description.getScene().setRoot(root);

                } catch (IOException e) {
                    // Handle IO exception
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                // Handle SQL exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void setTextFields(Moy_Transport R){
        txtId.setText(String.valueOf(R.getId_transport()));
        txtTransport_Model.setText(R.getTransport_Model());
        txtTransport_Price.setText(String.valueOf(R.getTransport_Price()));
        txtTransport_Description.setText(R.getTransport_Description());
        txtDisponibility.setText(R.getDisponibility());
        label.setText(R.getTransport_Picture());
    }

}


