package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import tn.esprit.entities.Booking;
import tn.esprit.services.IBooking;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AfficherBooking implements Initializable {

    @FXML
    private Button add;

    @FXML
    private TableView<Booking> TVB;

    @FXML
    private Button edit;

    @FXML
    private Button home;

    @FXML
    private Button refresh;

    @FXML
    private Button delete;

    private final IBooking iBooking = new IBooking();

    @FXML
    private HBox cartev;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Booking> bookings = iBooking.lister();
            for (Booking booking : bookings) {
                FXMLLoader carteArticleLoader = new FXMLLoader(getClass().getResource("/carteBooking.fxml"));
                HBox carteArticleBox = carteArticleLoader.load();
                carteBooking carte = carteArticleLoader.getController();

                carte.setId(String.valueOf(booking.getId()));
                carte.setDebut((Date) booking.getDebut());
                carte.setFin((Date) booking.getFin());
                carte.setAccomodationId(String.valueOf(booking.getAccomodationId()));
                carte.setUserId(String.valueOf(booking.getUserId()));

                cartev.getChildren().add(carteArticleBox);
            }
        } catch (IOException e) {
            System.err.println("Error loading carteBooking.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void actionRefresh(ActionEvent event) {
        refreshTable();
    }

    @FXML
    void actionAddBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AjouterBooking.fxml")));
            add.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }    }

    @FXML
    void actionUpdateBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/UpdateBooking.fxml")));
            edit.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    @FXML
    void actionDeleteBooking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DeleteBooking.fxml"));
            delete.getScene().setRoot(root);

        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("GG");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private void refreshTable() {
        try {
            ArrayList<Booking> bookings = iBooking.lister();
            TVB.getItems().clear();
            TVB.getItems().addAll(bookings);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error occurred while loading bookings: " + e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void actionHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/Home.fxml")));
            home.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }

}
