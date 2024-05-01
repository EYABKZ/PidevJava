package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import tn.esprit.entities.Calendar;
import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceCalendar;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ModifierCalendarController {

    ServiceCalendar sv = new ServiceCalendar();

    private Calendar calendarToUpdate;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtTitle;

    @FXML
    private DatePicker txtStart;

    @FXML
    private DatePicker txtEnd;

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

    public void setTxtId(String txtId) { this.txtId.setText(txtId); }
    public void setTxtTitle(String txtTitle) {this.txtTitle.setText(txtTitle); }
    public void setTxtStart(String txtStart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(txtStart, formatter);
        this.txtStart.setValue(date);
    }
    public void setTxtEnd(String txtEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.parse(txtEnd, formatter);
        this.txtEnd.setValue(date);
    }
    public void setTxtDescription(String txtDescription) { this.txtDescription.setText(txtDescription); }
    public void setTxtAll_Day(String txtAll_Day) { this.txtAll_Day.setText(txtAll_Day); }
    public void setTxtBackgroundColor(String txtBackgroundColor) { this.txtBackgroundColor.setValue(javafx.scene.paint.Color.web(txtBackgroundColor)); }
    public void setTxtBorderColor(String txtBorderColor) { this.txtBorderColor.setValue(javafx.scene.paint.Color.web(txtBorderColor)); }
    public void setTxtTextColor(String txtTextColor) { this.txtTextColor.setValue(javafx.scene.paint.Color.web(txtTextColor)); }
    public void setTxtTransport_Model(Moy_Transport txtTransport_Model) { this.txtTransport_Model.setValue(txtTransport_Model); }
    public void setTxtPassenger_Count(String txtPassenger_Count) { this.txtPassenger_Count.setText(txtPassenger_Count); }

    @FXML
    void ModifierAction() {
        try {
            Calendar existingCalendar = new Calendar();


            Color color = txtBackgroundColor.getValue(); // Pour ColorPicker
            String backgroundColor = String.format("#%02x%02x%02x",
                    (int) Math.round(color.getRed() * 255),
                    (int) Math.round(color.getGreen() * 255),
                    (int) Math.round(color.getBlue() * 255)
            );
            color = txtBorderColor.getValue(); // Pour ColorPicker
            String borderColor = String.format("#%02x%02x%02x",
                    (int) Math.round(color.getRed() * 255),
                    (int) Math.round(color.getGreen() * 255),
                    (int) Math.round(color.getBlue() * 255)
            );

            color = txtTextColor.getValue(); // Pour ColorPicker
            String textColor = String.format("#%02x%02x%02x",
                    (int) Math.round(color.getRed() * 255),
                    (int) Math.round(color.getGreen() * 255),
                    (int) Math.round(color.getBlue() * 255)
            );
            existingCalendar.setId(Integer.parseInt(txtId.getText()));
            existingCalendar.setTitle(txtTitle.getText());
            existingCalendar.setStart(String.valueOf(txtStart.getValue()));
            existingCalendar.setEnd(String.valueOf(txtEnd.getValue()));
            existingCalendar.setDescription(txtDescription.getText());
            existingCalendar.setAll_Day(Integer.parseInt(txtAll_Day.getText()));
            existingCalendar.setBackground_Color(backgroundColor);
            existingCalendar.setBorder_Color(borderColor);
            existingCalendar.setText_Color(textColor);
            existingCalendar.setId_transport(txtTransport_Model.getValue());
            existingCalendar.setPassenger_Count(Integer.parseInt(txtPassenger_Count.getText()));

            // Call the modifier method with the modified Transport object
            sv.modifier(existingCalendar);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Transport modifiée avec succès");
            alert.showAndWait();

            // Reload the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCM.fxml"));
            Parent root = loader.load();

            AfficherCControllers afficherCControllers = loader.getController();
            List<Calendar> allCalendars = sv.recuperer();

            // Add all transports to the ListView
            ObservableList<Calendar> items = FXCollections.observableArrayList(allCalendars);
            afficherCControllers.AfficherList.setItems(items);

            // Use a CellFactory to display the Transport_Model as the text of the list items
            afficherCControllers.AfficherList.setCellFactory(param -> new ListCell<Calendar>() {
                protected void updateItem(Calendar item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getTitle() == null) {
                        setText(null);
                    } else {
                        setText(item.getTitle());
                    }
                }
            });

            // Set an on-click listener for the ListView items
            afficherCControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Fetch the details of the selected transport
                Calendar selectedEvent = newValue;

                // Affichez les détails de l'événement sélectionné
                afficherCControllers.setTitle(selectedEvent.getTitle());
                afficherCControllers.setStart(selectedEvent.getStart());
                afficherCControllers.setEnd(selectedEvent.getEnd());
                afficherCControllers.setDescription(selectedEvent.getDescription());
                afficherCControllers.setAll_Day(String.valueOf(selectedEvent.getAll_Day()));
                afficherCControllers.setBackgroundColor(selectedEvent.getBackground_Color());
                afficherCControllers.setBorderColor(selectedEvent.getBorder_Color());
                afficherCControllers.setTextColor(selectedEvent.getText_Color());
                afficherCControllers.setTransport_Model(selectedEvent.getId_transport());
                afficherCControllers.setPassenger_Count(String.valueOf(selectedEvent.getPassenger_Count()));
                afficherCControllers.setId(String.valueOf(selectedEvent.getId()));
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

    public void initialize() throws SQLException {
        List<Moy_Transport> transports = sv.recupererTousLesTransports();
        ObservableList<Moy_Transport> observableList = FXCollections.observableArrayList(transports);
        txtTransport_Model.setItems(observableList);

        txtTransport_Model.setCellFactory(new Callback<ListView<Moy_Transport>, ListCell<Moy_Transport>>() {

            public ListCell<Moy_Transport> call(ListView<Moy_Transport> param) {
                return new ListCell<Moy_Transport>() {
                    @Override
                    protected void updateItem(Moy_Transport item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getTransport_Model()); // Affichez la propriété que vous voulez
                        }
                    }
                };
            }
        });
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCalendarM.fxml"));
                try {
                    Parent root = loader.load();

                    AjouterCalendarController a = loader.getController();
                    a.setTxtTitle("");
                    a.setStart("");
                    a.setEnd("");
                    a.setDescription("");
                    a.setAll_Day("");
                    a.setBackgroundColor("");
                    a.setBorderColor("");
                    a.setTextColor("");
                    Moy_Transport transport = new Moy_Transport();
                    a.setTransport_Model(transport);
                    a.setPassenger_Count("");

                    txtId.getScene().setRoot(root);

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


