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
            label.setText(selectedFile.getName());
            String imageUrl = "file:" + selectedFile.getAbsolutePath();
            Image image = new Image(imageUrl);
            Transport_Picture.setImage(image);
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

            String imageUrl = Transport_Picture.getImage().getUrl();
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherT.fxml"));
            Parent root = loader.load();

            AfficherTControllers afficherTControllers = loader.getController();
            List<Moy_Transport> allTransports = sv.recuperer();

            // Add all transports to the ListView
            ObservableList<Moy_Transport> items = FXCollections.observableArrayList(allTransports);
            afficherTControllers.AfficherList.setItems(items);

            // Use a CellFactory to display the Transport_Model as the text of the list items
            afficherTControllers.AfficherList.setCellFactory(param -> new ListCell<Moy_Transport>() {
                @Override
                protected void updateItem(Moy_Transport item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getTransport_Model() == null) {
                        setText(null);
                    } else {
                        setText(item.getTransport_Model());
                    }
                }
            });

            // Set an on-click listener for the ListView items
            afficherTControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Fetch the details of the selected transport
                Moy_Transport selectedTransport = newValue;

                // Display the details of the selected transport
                afficherTControllers.settxtTransport_Model(selectedTransport.getTransport_Model());
                afficherTControllers.setTransport_Picture(selectedTransport.getTransport_Picture());
                afficherTControllers.settxtTransport_Price(String.valueOf(selectedTransport.getTransport_Price()));
                afficherTControllers.settxtTransport_Description(selectedTransport.getTransport_Description());
                afficherTControllers.settxtDisponibility(selectedTransport.getDisponibility());
                afficherTControllers.settxtId(String.valueOf(selectedTransport.getId_transport()));
            });

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterTransport.fxml"));
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


}


