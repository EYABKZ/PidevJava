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
import javafx.scene.control.TableView;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class ModifierVoyage {

    @FXML
    private ComboBox<String> vehiculeid;

    @FXML
    private ComboBox<String> hebergementid;

    @FXML
    private ComboBox<String> evenementid;

    @FXML
    private ComboBox<String> utilisateurid;

    @FXML
    private DatePicker departid;

    @FXML
    private DatePicker arriveeid;

    @FXML
    private Button ajouterButton;

    private VoyageService voyageService;
    private VehiculeService vehiculeService;
    private HebergementService hebergementService;
    private EvenementService evenementService;
    private UtilisateurService utilisateurService;

    // Add a field to store the selected voyage from the TableView
    private Voyage selectedVoyage;

    // Add a field to store the TableView reference
    private TableView<Voyage> tableView;

    public void initData(Voyage selectedVoyage, TableView<Voyage> tableView) {
        this.selectedVoyage = selectedVoyage;
        this.tableView = tableView;

        // Populate fields with the selected voyage details
        if (selectedVoyage != null) {
            setVoyageInfo(selectedVoyage.getVehicule_id(), selectedVoyage.getHebergement_id(),
                    selectedVoyage.getEvenement_id(), selectedVoyage.getUtilisateur_id(),
                    selectedVoyage.getDepart(), selectedVoyage.getArrivee());
        } else {
            // Clear fields if no voyage is selected
            clearFields();
        }
    }

    private void clearFields() {
        vehiculeid.setValue("");
        hebergementid.setValue("");
        evenementid.setValue("");
        utilisateurid.setValue("");
        departid.setValue(null);
        arriveeid.setValue(null);
    }

    public ModifierVoyage() {
        voyageService = new VoyageService();
        vehiculeService = new VehiculeService();
        hebergementService = new HebergementService();
        evenementService = new EvenementService();
        utilisateurService = new UtilisateurService();
    }

    public void setVoyageInfo(int vehicule, int hebergement, int evenement, int utilisateur, Date depart, Date arrivee) {
        vehiculeid.setValue(String.valueOf(vehicule));
        hebergementid.setValue(String.valueOf(hebergement));
        evenementid.setValue(String.valueOf(evenement));
        utilisateurid.setValue(String.valueOf(utilisateur));
        departid.setValue(depart.toLocalDate());
        arriveeid.setValue(arrivee.toLocalDate());
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
            // Populate Utilisateur ComboBox
            List<String> utilisateurs = utilisateurService.getAllUsers();
            utilisateurid.setItems(FXCollections.observableArrayList(utilisateurs));
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print the stack trace for debugging
            // Handle the exception or display an error message as needed
        }
    }

    @FXML
    private void modifierVoyage() {
        try {
            if (selectedVoyage == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please select a voyage to modify.");
                return;
            }

            String vehicule = vehiculeid.getValue();
            String hebergement = hebergementid.getValue();
            String evenement = evenementid.getValue();
            String utilisateur = utilisateurid.getValue();
            Date depart = departid.getValue() != null ? Date.valueOf(departid.getValue()) : null;
            Date arrivee = arriveeid.getValue() != null ? Date.valueOf(arriveeid.getValue()) : null;

            // Check if any field has been modified
            if (selectedVoyage.getVehicule_id() == Integer.parseInt(vehicule) &&
                    selectedVoyage.getHebergement_id() == Integer.parseInt(hebergement) &&
                    selectedVoyage.getEvenement_id() == Integer.parseInt(evenement) &&
                    selectedVoyage.getUtilisateur_id() == Integer.parseInt(utilisateur) &&
                    selectedVoyage.getDepart().equals(depart) &&
                    selectedVoyage.getArrivee().equals(arrivee)) {
                showAlert(Alert.AlertType.WARNING, "No Changes", "No changes detected.");
                return;
            }

            // Update the selected voyage with modified values
            selectedVoyage.setVehicule_id(Integer.parseInt(vehicule));
            selectedVoyage.setHebergement_id(Integer.parseInt(hebergement));
            selectedVoyage.setEvenement_id(Integer.parseInt(evenement));
            selectedVoyage.setUtilisateur_id(Integer.parseInt(utilisateur));
            selectedVoyage.setDepart(depart);
            selectedVoyage.setArrivee(arrivee);

            // Call the modifier method of VoyageService
            voyageService.modifier(selectedVoyage);

            showAlert(Alert.AlertType.INFORMATION, "Modification Successful", "Voyage modified successfully.");

        } catch (SQLException | NullPointerException | NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while modifying the voyage.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}