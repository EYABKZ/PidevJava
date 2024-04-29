package tn.esprit.controllers;/*
//  * To change this license header, choose License Headers in Project Properties.
//  * To change this template file, choose Tools | Templates
//  * and open the template in the editor.
//  */


import javafx.fxml.Initializable;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;


import java.io.File;
import java.io.IOException;

import tn.esprit.entities.Pdf;


import java.util.logging.Level;
import java.util.logging.Logger;



// /**
//  * FXML Controller class
//  *
//  * @author ASUS
//  */
public class AdminController implements Initializable {

    ServicePersonne sv = new ServicePersonne();

    @FXML
    private TableView<Personne> tableviewUser;
    //@FXML
    //private TableColumn<?, ?> idUser;
    @FXML
    private TableColumn<?, ?> lastnameUser;

    @FXML
    private TableColumn<?, ?> EmailUser;
    //@FXML
    //private TableColumn<?, ?> PasswordUser;

    @FXML
    private TextField Recherche_User;

    @FXML
    private TextField txtStat;


    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;
    Personne user = null ;

    public AdminController() {
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showRec();
            searchRec();
            stat();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void exit(){
        System.exit(0);
    }

    public ObservableList<Personne> getPersonneList() throws SQLException {
        ObservableList<Personne> userList = FXCollections.observableArrayList();

        try {
            userList.addAll(sv.recuperer());
            System.out.println(userList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return userList;
    }


    public void showRec() throws SQLException {

        ObservableList<Personne> list = getPersonneList();
        //idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastnameUser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
        //PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableviewUser.setItems(list);

    }

    @FXML
    private void refresh() throws SQLException {
        ObservableList<Personne> list = getPersonneList();
        //idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastnameUser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
        //PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableviewUser.setItems(list);
    }

    @FXML
    private void SupprimerUser() throws SQLException {

        Personne user = (Personne) tableviewUser.getSelectionModel().getSelectedItem();
        sv.supprimer(user.getId());
        refresh();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Travel Me :: Error Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilisateur supprimé");
        alert.showAndWait();
        stat();

    }

    @FXML
    private void ModifierUser(ActionEvent event) throws SQLException {
        Personne user = (Personne) tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/ModifierP.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ModifierPersonneController muc = loader.getController();
        // mrc.setUpdate(true);
        muc.setTextFields(user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();
        stat();

    }


    @FXML
    private void searchRec() throws SQLException {
        //idUser.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastnameUser.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        EmailUser.setCellValueFactory(new PropertyValueFactory<>("email"));
        //PasswordUser.setCellValueFactory(new PropertyValueFactory<>("password"));

        ObservableList<Personne> list = getPersonneList();
        tableviewUser.setItems(list);

        FilteredList<Personne> filteredData = new FilteredList<>(list, b -> true);

        Recherche_User.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.trim().isEmpty()) {

                    return true; // Show all items if filter is empty
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return person.getEmail().toLowerCase().contains(lowerCaseFilter)
                        || person.getLastname().toLowerCase().contains(lowerCaseFilter);

            });
        });
        SortedList<Personne> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);
    }




    @FXML
    private void pdf_print(ActionEvent event) throws IOException, NoSuchMethodException, InstantiationException, InvocationTargetException, IllegalAccessException, SQLException {


        Personne voy = tableviewUser.getSelectionModel().getSelectedItem();

        Pdf pd=new Pdf();
        try{
            pd.GeneratePdf(""+voy.getLastname()+"",voy,voy.getId());
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF");
            alert.setHeaderText(null);
            alert.setContentText("!!!PDF exported!!!");
            alert.showAndWait();
            System.out.println("impression done");
        } catch (Exception ex) {
            Logger.getLogger(ServicePersonne.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert= new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("!!!Selectioner une Voyage!!!");
            alert.showAndWait();
        }



    }

    private void stat() {
        try {
            String resultSet= sv.stat_count();
            txtStat.setText(String.valueOf(resultSet));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



}
