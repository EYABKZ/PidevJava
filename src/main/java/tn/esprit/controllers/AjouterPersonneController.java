package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AjouterPersonneController {

    ServicePersonne sv = new ServicePersonne();

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;


    public void setTxtLastname(String txtLastname) {
        this.txtLastname.setText(txtLastname);
    }


    public void setTxtEmail(String txtEmail) {
        this.txtEmail.setText(txtEmail);
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword.setText(txtPassword);
    }

    private boolean isValidEmail(String email) {
        // Expression régulière pour valider l'email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Expression régulière pour valider le mot de passe
        // Cette expression vérifie que le mot de passe contient au moins une lettre majuscule,
        // une lettre minuscule, un chiffre, et a une longueur d'au moins 8 caractères
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }



    @FXML
    void ajouterAction(javafx.event.ActionEvent event) {

        try {
            // Validate email
            if (!isValidEmail(txtEmail.getText())) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setContentText("Invalid email , Please enter a valid email address.");
                alert.show();
                return;
            }

            // Validate lastname length
            if (txtLastname.getText().length() <= 7) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setContentText("Invalid lastname , Lastname should be longer than 7 characters.");
                alert.show();
                return;
            }

            // Validate password
            if (!isValidPassword(txtPassword.getText())) {
                event.consume();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Attention");
                alert.setContentText("Password should contain one Upper&Lowercase,digit, and at least 8 characters long.");
                alert.show();
                return;
            }
                Personne p = new Personne(txtLastname.getText(), txtEmail.getText(), txtPassword.getText());
                int generatedId = sv.ajouter(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("Personne Ajouter avec succes");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));

                try {
                    Parent root = loader.load();
                    AfficherPControllers afficherPControllers = loader.getController();
                    afficherPControllers.setTxtLastname(txtLastname.getText());
                    afficherPControllers.setTxtEmail(txtEmail.getText());
                    afficherPControllers.setTxtPassword(txtPassword.getText());

                    afficherPControllers.setTxtId(String.valueOf(generatedId));


                    // Debug statement to check if root is loaded successfully
                    System.out.println("FXML loaded successfully.");

                    txtEmail.getScene().setRoot(root);
                } catch (IOException e) {
                    // Instead of just printing, handle the IOException
                    e.printStackTrace();
                }

            } catch(SQLException e){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();

            }

    }

    public void goToLogin(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignIn.fxml"));

        try {
            Parent root = loader.load();

                SignInController SignInController = loader.getController();
                 SignInController.setEmail_signin("");
                 SignInController.setPassword_signin("");


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully sign in.");

                 txtEmail.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

}
