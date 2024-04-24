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
            Moy_Transport T= new Moy_Transport(imagePath, txtTransport_Model.getText(), Integer.parseInt(txtTransport_Price.getText()), txtTransport_Description.getText(), txtDisponibility.getText());
            int generatedId = sv.ajouter(T);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Transport Ajouter avec succes");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherT.fxml"));

            try {
                Parent root = loader.load();
                AfficherTControllers afficherTControllers = loader.getController();

                // Fetch all Transport_Model from the database
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

                // Debug statement to check if root is loaded successfully
                System.out.println("FXML loaded successfully.");

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
