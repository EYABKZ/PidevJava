package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Pattern;

public class ModifierPersonneController {

    ServicePersonne sv = new ServicePersonne();

    private Personne personneToUpdate;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtId;

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
    void ModifierAction(javafx.event.ActionEvent event) {
        try {
            // Retrieve the existing Personne object from some data source
            Personne existingPersonne = new Personne();

            // Modify the existing Personne object
            existingPersonne.setLastname(txtLastname.getText());
            existingPersonne.setEmail(txtEmail.getText());
            existingPersonne.setPassword(txtPassword.getText());
            existingPersonne.setId(Integer.parseInt(txtId.getText()));

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

            // Call the modifier method with the modified Personne object
            sv.modifier(existingPersonne);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Personne modifiée avec succès");
            alert.show();

            // Reload the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));

            try {
                Parent root = loader.load();

                AfficherPControllers afficherPControllers = loader.getController();
                afficherPControllers.setTxtLastname(txtLastname.getText());
                afficherPControllers.setTxtEmail(txtEmail.getText());
                afficherPControllers.setTxtPassword(txtPassword.getText());

                afficherPControllers.setTxtId(txtId.getText());
                txtEmail.getScene().setRoot(root);

            } catch (IOException e) {
                // Handle IO exception
                e.printStackTrace();
            }
            }catch (SQLException e) {
            // Handle SQL exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void SupprimerAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this user?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
        try {

            sv.supprimer(Integer.parseInt(txtId.getText()));

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Personne supprimé avec succès");
            alert.show();

            // Reload the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonne.fxml"));

            try {
                Parent root = loader.load();

                AjouterPersonneController a = loader.getController();
                a.setTxtLastname("");
                a.setTxtEmail("");
                a.setTxtPassword("");


                txtEmail.getScene().setRoot(root);

            } catch (IOException e) {
                // Handle IO exception
                e.printStackTrace();
            }
        }catch (SQLException e) {
            // Handle SQL exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    }

    public void setTextFields(Personne R){
        txtId.setText(String.valueOf(R.getId()));
        txtLastname.setText(R.getLastname());
        txtEmail.setText(R.getEmail());
        txtPassword.setText(R.getPassword());
    }
}


