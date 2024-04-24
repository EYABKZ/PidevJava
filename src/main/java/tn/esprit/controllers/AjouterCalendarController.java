package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import tn.esprit.entities.Calendar;
import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceCalendar;

import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AjouterCalendarController {

    ServiceCalendar sv = new ServiceCalendar();

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

    @FXML
    void ajouterAction(ActionEvent event) {
        try {

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

            LocalDate date = txtStart.getValue(); // Pour DatePicker
            String startDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            date = txtEnd.getValue(); // Pour DatePicker
            String endDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            Calendar C = new Calendar(
                    txtTitle.getText(),
                    startDate,
                    endDate,
                    txtDescription.getText(),
                    Integer.parseInt(txtAll_Day.getText()),
                    backgroundColor,
                    borderColor,
                    textColor,
                    Integer.parseInt(txtPassenger_Count.getText()),
                    txtTransport_Model.getValue()
            );

            int generatedId = sv.ajouter(C);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Reservation Ajouter avec succes");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherC.fxml"));

            try {
                Parent root = loader.load();
                AfficherCControllers afficherCControllers = loader.getController();

                // Récupérez tous les événements de calendrier de la base de données
                List<Calendar> allEvents = sv.recuperer();

                // Ajoutez tous les événements à la ListView
                ObservableList<Calendar> items = FXCollections.observableArrayList(allEvents);
                afficherCControllers.AfficherList.setItems(items);

                // Utilisez une CellFactory pour afficher le titre de l'événement comme le texte des éléments de la liste
                afficherCControllers.AfficherList.setCellFactory(param -> new ListCell<Calendar>() {
                    @Override
                    protected void updateItem(Calendar item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getTitle() == null) {
                            setText(null);
                        } else {
                            setText(item.getTitle());
                        }
                    }
                });

                // Définissez un écouteur de clic pour les éléments de la ListView
                afficherCControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    // Récupérez les détails de l'événement sélectionné
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

                // Déclaration de débogage pour vérifier si le FXML est chargé avec succès
                System.out.println("FXML loaded successfully.");

                txtDescription.getScene().setRoot(root);
            } catch (IOException e) {
                // Au lieu de simplement imprimer, gérez l'IOException
                e.printStackTrace();
            }


        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();

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

    public void setTxtTitle(String txtTitle) {
        this.txtTitle.setText(txtTitle);
    }
    public void setStart(String start) {
        if (start == null || start.isEmpty()) {
            this.txtStart.setValue(null);
        } else {
            this.txtStart.setValue(LocalDate.parse(start));
        }
    }

    public void setEnd(String end) {
        if (end == null || end.isEmpty()) {
            this.txtEnd.setValue(null);
        } else {
            this.txtEnd.setValue(LocalDate.parse(end));
        }
    }

    public void setDescription(String description) {
        this.txtDescription.setText(description);
    }
    public void setAll_Day(String all_Day) {
        this.txtAll_Day.setText(all_Day);
    }
    public void setBackgroundColor(String backgroundColor) {
        if (backgroundColor != null && !backgroundColor.isEmpty()) {
            this.txtBackgroundColor.setValue(Color.valueOf(backgroundColor));
        } else {
            // Gérer le cas où backgroundColor est null ou vide
        }
    }

    public void setBorderColor(String borderColor) {
        if (borderColor != null && !borderColor.isEmpty()) {
            this.txtBorderColor.setValue(Color.valueOf(borderColor));
        } else {
            // Gérer le cas où borderColor est null ou vide
        }
    }

    public void setTextColor(String textColor) {
        if (textColor != null && !textColor.isEmpty()) {
            this.txtTextColor.setValue(Color.web(textColor));
        } else {
            // Gérer le cas où textColor est null ou vide
        }
    }

    public void setTransport_Model(Moy_Transport transport_Model) {
        this.txtTransport_Model.setValue(transport_Model);
    }
    public void setPassenger_Count(String passenger_Count) {
        this.txtPassenger_Count.setText(passenger_Count);
    }
}
