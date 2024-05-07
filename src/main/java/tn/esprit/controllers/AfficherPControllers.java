package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AfficherPControllers {

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtId;


    private Stage stage;
    private Scene scene;
    private Parent root;

    //ImageView profileImg;

    //Image profile_picture = new Image(getClass().getResourceAsStream("travel_car"));
    //profileImg.setImage(profile_picture);

    public void setTxtLastname(String txtLastname) {
        this.txtLastname.setText(txtLastname);
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword.setText(txtPassword);
    }

    public void setTxtId(String txtId) {
        this.txtId.setText(txtId);
    }



    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierP.fxml"));

        try {
            Parent root = loader.load();
            ModifierPersonneController modifierController = loader.getController();
            modifierController.setTxtLastname(txtLastname.getText());
            modifierController.setTxtEmail(txtEmail.getText());
            modifierController.setTxtPassword(txtPassword.getText());
            modifierController.setTxtId(txtId.getText());

            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            txtEmail.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

    @FXML
    private void goToHome(ActionEvent event) {
        try {

            Parent parent = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }


}


