package tn.esprit.controllers;

import tn.esprit.entities.Voyage;
import tn.esprit.services.EvenementService;
import tn.esprit.services.HebergementService;
import tn.esprit.services.VehiculeService;
import tn.esprit.services.VoyageService;
import tn.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class AjouterVoyage {

    @FXML
    private ComboBox<String> vehiculeid;

    @FXML
    private ComboBox<String> hebergementid;

    @FXML
    private ComboBox<String> evenementid;

    @FXML
    private ComboBox<String> utilisateurid;

    @FXML
    private DatePicker departdate;

    @FXML
    private DatePicker arriveedate;

    @FXML
    private Button ajouterButton;

    private VoyageService voyageService;
    private VehiculeService vehiculeService;
    private HebergementService hebergementService;
    private EvenementService evenementService;
    private UtilisateurService utilisateurService;

    public AjouterVoyage() {
        voyageService = new VoyageService();
        vehiculeService = new VehiculeService();
        hebergementService = new HebergementService();
        evenementService = new EvenementService();
        utilisateurService = new UtilisateurService();
    }

    @FXML
    private void initialize() {
        // Populate Vehicule ComboBox
        try {
            List<String> vehicules = vehiculeService.getAllVehicules();
            vehiculeid.setItems(FXCollections.observableArrayList(vehicules));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        // Populate Hebergement ComboBox
        try {
            List<String> hebergements = hebergementService.getAllHebergements();
            hebergementid.setItems(FXCollections.observableArrayList(hebergements));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        // Populate Evenement ComboBox
        try {
            List<String> evenements = evenementService.getAllEvenements();
            evenementid.setItems(FXCollections.observableArrayList(evenements));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }

        try {
            // Code that may throw SQLException
            List<String> utilisateurs = utilisateurService.getAllUsers();
            utilisateurid.setItems(FXCollections.observableArrayList(utilisateurs));
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print the stack trace for debugging
            // Handle the exception or display an error message as needed
        }
    }

    @FXML
    private void ajouterVoyage() {
        // Retrieve selected values from ComboBoxes and DatePickers
        String selectedVehicule = vehiculeid.getValue();
        String selectedHebergement = hebergementid.getValue();
        String selectedEvenement = evenementid.getValue();
        String selectedUtilisateur = utilisateurid.getValue();
        Date departDate = departdate.getValue() != null ? Date.valueOf(departdate.getValue()) : null;
        Date arriveeDate = arriveedate.getValue() != null ? Date.valueOf(arriveedate.getValue()) : null;

        // Perform validation checks
        if (selectedVehicule == null || selectedHebergement == null || selectedEvenement == null || selectedUtilisateur == null || departDate == null || arriveeDate == null || departDate.after(arriveeDate)) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur");
            errorAlert.setHeaderText("Erreur lors de l'ajout du voyage");

            if (departDate != null && arriveeDate != null && departDate.after(arriveeDate)) {
                errorAlert.setContentText("La date de départ ne peut pas être ultérieure à la date d'arrivée.");
            } else {
                errorAlert.setContentText("Veuillez sélectionner tous les champs obligatoires.");
            }
            errorAlert.showAndWait();
            return;
        }

        // Log the selected values before fetching IDs
        System.out.println("Selected Vehicule: " + selectedVehicule);
        System.out.println("Selected Hebergement: " + selectedHebergement);
        System.out.println("Selected Evenement: " + selectedEvenement);
        System.out.println("Selected Utilisateur: " + selectedUtilisateur);
        System.out.println("Depart Date: " + departDate);
        System.out.println("Arrivee Date: " + arriveeDate);

        // Fetch IDs from respective tables using tn.esprit.services
        int vehiculeId = -1;
        int hebergementId = -1;
        int evenementId = -1;
        int utilisateurId = -1;

        vehiculeId = vehiculeService.getVehiculeId(selectedVehicule);
        hebergementId = hebergementService.getHebergementId(selectedHebergement);
        evenementId = evenementService.getEvenementId(selectedEvenement);
        utilisateurId = Integer.parseInt(selectedUtilisateur);

        // Log the fetched IDs
        System.out.println("Fetched Vehicule ID: " + vehiculeId);
        System.out.println("Fetched Hebergement ID: " + hebergementId);
        System.out.println("Fetched Evenement ID: " + evenementId);
        System.out.println("Fetched Utilisateur ID: " + utilisateurId);

        // Create a new Voyage object with the retrieved values
        Voyage newVoyage = new Voyage();
        newVoyage.setVehicule_id(vehiculeId);
        newVoyage.setHebergement_id(hebergementId);
        newVoyage.setEvenement_id(evenementId);
        newVoyage.setUtilisateur_id(utilisateurId);
        newVoyage.setDepart(departDate);
        newVoyage.setArrivee(arriveeDate);

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Add Voyage");
        alert.setContentText("Are you sure you want to add this voyage?");

        // Customize the buttons in the confirmation dialog
        ButtonType confirmButton = new ButtonType("Yes");
        ButtonType cancelButton = new ButtonType("No");

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        // Show and wait for user response
        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == confirmButton) {
                try {
                    // Add the new voyage to the database

                    //voyageService.ajouter(newVoyage);
                    int generatedId = voyageService.ajouter(newVoyage);

                    // Optionally, display a success message or update UI
                    System.out.println("Voyage ajouté avec succès !");
                } catch (Exception e) {
                    if (selectedEvenement == null || selectedHebergement == null || selectedVehicule == null || selectedUtilisateur == null) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText("Erreur lors de l'ajout du voyage");
                        errorAlert.setContentText("Veuillez saisir tous les champs");
                        errorAlert.showAndWait();
                        return;
                    }
                    System.err.println("Erreur lors de l'ajout du voyage : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
