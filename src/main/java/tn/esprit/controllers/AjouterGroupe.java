package tn.esprit.controllers;

import tn.esprit.services.UtilisateurService;
import tn.esprit.services.VoyageService;
import tn.esprit.services.GroupeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import tn.esprit.entities.Groupe;

import java.sql.SQLException;
import java.util.List;

public class AjouterGroupe {

    private VoyageService voyageService;
    private UtilisateurService utilisateurService;
    private GroupeService groupeService;

    @FXML
    private ComboBox<String> voyageid;

    @FXML
    private ComboBox<String> utilisateurid;

    @FXML
    private TextField nomber;
    public AjouterGroupe() {
        voyageService = new VoyageService();
        utilisateurService = new UtilisateurService();
        groupeService = new GroupeService();
    }

    @FXML
    private void initialize() {
        try {
            List<String> utilisateurs = utilisateurService.getAllUsers();
            utilisateurid.setItems(FXCollections.observableArrayList(utilisateurs));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            List<String> voyages = voyageService.getAllVoyages();
            voyageid.setItems(FXCollections.observableArrayList(voyages));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ajouterGroupe() {
        String selectedVoyage = voyageid.getValue();
        String selectedUtilisateur = utilisateurid.getValue();
        String nombreText = nomber.getText(); // Retrieve the value from the nombre TextField

        // Validate that ComboBoxes have selected values and nombre is a valid integer
        if (selectedVoyage == null || selectedUtilisateur == null || nombreText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Champs incomplets");
            alert.setContentText("Veuillez sélectionner un voyage, un utilisateur et saisir un nombre.");
            alert.show();
            return;
        }

        // Validate that nombre contains only integers
        try {
            int nombre = Integer.parseInt(nombreText); // Parse the value to int

            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir ajouter ce groupe ?");
            confirmationAlert.setContentText("Cliquez sur OK pour confirmer.");

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    int voyageId;
                    int utilisateurId;

                    try {
                        voyageId = Integer.parseInt(selectedVoyage);
                        utilisateurId = Integer.parseInt(selectedUtilisateur);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur lors de la conversion de l'identifiant en entier : " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }

                    Groupe newGroupe = new Groupe();
                    newGroupe.setVoyage_id(voyageId);
                    newGroupe.setUtilisateur_id(utilisateurId);
                    newGroupe.setNumber_membre(nombre); // Set the nombre for the new group

                    try {
                        groupeService.ajouter(newGroupe);
                        System.out.println("Groupe ajouté avec succès !");
                    } catch (SQLException e) {
                        System.err.println("Erreur lors de l'ajout du groupe : " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Le nombre de membres doit être un entier.");
            alert.show();
        }
    }

}
