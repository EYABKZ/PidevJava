package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tn.esprit.entities.Calendar;
import tn.esprit.entities.Moy_Transport;

import java.io.IOException;

public class AfficherCControllers {

    public ListView <Calendar> AfficherList;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTitle;

    @FXML
    Label txtStart = new Label();

    @FXML
    Label txtEnd = new Label();

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtAll_Day;

    @FXML
    private ColorPicker txtBackgroundColor;

    @FXML
    private ColorPicker txtBorderColor;

    @FXML
    private ColorPicker txtTextColor;

    @FXML
    private ComboBox<Moy_Transport> txtTransport_Model;

    @FXML
    private TextField txtPassenger_Count;

    public void setTitle(String title) {
        this.txtTitle.setText(title);
    }

    public void setStart(String start) {
       this.txtStart.setText(start);
    }

    public void setEnd(String end) {
        this.txtEnd.setText(end);
    }

    public void setDescription(String description) {
        this.txtDescription.setText(description);
    }

    public void setAll_Day(String s) {
        this.txtAll_Day.setText(s);
    }

    public void setBackgroundColor(String backgroundColor) {
        // Convertir la chaîne de caractères en hexadécimal en un objet Color
        Color color = Color.web(backgroundColor);

        // Définir la couleur du ColorPicker
        this.txtBackgroundColor.setValue(color);
    }

    public void setId(String id) {
        this.txtId.setText(id);
    }

    public void setBorderColor(String borderColor) {
        // Convertir la chaîne de caractères en hexadécimal en un objet Color
        Color color = Color.web(borderColor);

        // Définir la couleur du ColorPicker
        this.txtBorderColor.setValue(color);
    }

    public void setTextColor(String textColor) {
        // Convertir la chaîne de caractères en hexadécimal en un objet Color
        Color color = Color.web(textColor);

        // Définir la couleur du ColorPicker
        this.txtTextColor.setValue(color);
    }

    public void setTransport_Model(Moy_Transport transport) {
        this.txtTransport_Model.setValue(transport);
    }

    public void setPassenger_Count(String s) {
        this.txtPassenger_Count.setText(s);
    }


    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCM.fxml"));

        try {
            Parent root = loader.load();
            ModifierCalendarController modifierController = loader.getController();
            modifierController.setTxtTitle(txtTitle.getText());
            modifierController.setTxtStart(txtStart.getText());
            modifierController.setTxtEnd(txtEnd.getText());
            modifierController.setTxtDescription(txtDescription.getText());
            modifierController.setTxtAll_Day(txtAll_Day.getText());
            modifierController.setTxtBackgroundColor(txtBackgroundColor.getValue().toString());
            modifierController.setTxtBorderColor(txtBorderColor.getValue().toString());
            modifierController.setTxtTextColor(txtTextColor.getValue().toString());
            modifierController.setTxtTransport_Model(txtTransport_Model.getValue());
            modifierController.setTxtPassenger_Count(txtPassenger_Count.getText());
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