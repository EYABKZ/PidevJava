package tn.esprit.controllers;/*
//  * To change this license header, choose License Headers in Project Properties.
//  * To change this template file, choose Tools | Templates
//  * and open the template in the editor.
//  */


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import javafx.fxml.Initializable;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.entities.Personne;
import tn.esprit.services.ServicePersonne;



import java.io.File;


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






    private void stat() {
        try {
            String resultSet= sv.stat_count();
            txtStat.setText(String.valueOf(resultSet));
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    private void imprimer(ActionEvent event) {
        try {
            Personne r = tableviewUser.getSelectionModel().getSelectedItem();
            OutputStream file = new FileOutputStream(new File("C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\" + r.getLastname() + ".pdf"));
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, file);
            document.open();
            ServicePersonne sm = new ServicePersonne();
            // Get user information


            document.add(new Paragraph("-- This page is CopyRighted by The Developers --"));
            document.add(new Paragraph("-- Alert : This pdf can be used only for administrational purposes! --\n\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.RED)));
            // Add placeholder image
            Image image = Image.getInstance(generateRandomProfilePicture()); // Change the path to your placeholder image
            document.add(image);
            document.add(new Paragraph(" ___________________________________________________________________________\n"));
            document.add(new Paragraph(" UserName :  " + r.getLastname() + "  \n"));
            document.add(new Paragraph(" User Email  :  " + r.getEmail() + "  \n"));
            document.add(new Paragraph(" ___________________________________________________________________________\n"));

            // Generate fake details
            document.add(new Paragraph(" Age: " + generateRandomAge() + "\n"));
            document.add(new Paragraph(" Gender: " + generateRandomGender() + "\n"));
            document.add(new Paragraph(" Date of Birth: " + generateRandomDOB() + "\n"));
            document.add(new Paragraph(" ___________________________________________________________________________\n"));

            document.add(new Paragraph(" University: " + generateRandomUniversity() + "\n"));
            document.add(new Paragraph(" Country: " + generateRandomCountry() + "\n"));
            document.add(new Paragraph(" Nationality: " + generateRandomNationality() + "\n"));



            document.add(new Paragraph(" ___________________________________________________________________________\n"));
            document.close();
            file.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Travel Me");
            alert.setHeaderText(null);
            alert.setContentText("pdf generated");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Travel Me :: Error Message");
            alert.setHeaderText(null);
            alert.setContentText("select a user");
            alert.showAndWait();
        }
    }

    // Method to generate random age (between 18 and 65 for example)
    private int generateRandomAge() {
        return (int)(Math.random() * (65 - 18 + 1) + 18);
    }

    // Method to generate random gender
    private String generateRandomGender() {
        String[] genders = {"Male", "Female"};
        return genders[(int)(Math.random() * genders.length)];
    }

    // Method to generate random date of birth
    private String generateRandomDOB() {
        // Generating a random date of birth within the last 50 years
        int year = (int)(Math.random() * 50) + 1970;
        int month = (int)(Math.random() * 12) + 1;
        int day = (int)(Math.random() * 28) + 1; // Simplification, assuming all months have 28 days
        return day + "/" + month + "/" + year;
    }

    // Method to generate random university
    private String generateRandomUniversity() {
        String[] universities = {"Harvard University", "Stanford University", "Massachusetts Institute of Technology (MIT)", "University of Cambridge", "University of Oxford"};
        return universities[(int)(Math.random() * universities.length)];
    }

    // Method to generate random country
    private String generateRandomCountry() {
        String[] countries = {"USA", "Canada", "UK", "France", "Germany", "Australia", "Japan", "China"};
        return countries[(int)(Math.random() * countries.length)];
    }

    // Method to generate random nationality
    private String generateRandomNationality() {
        String[] nationalities = {"American", "Canadian", "British", "French", "German", "Australian", "Japanese", "Chinese"};
        return nationalities[(int)(Math.random() * nationalities.length)];
    }

    // Method to generate random profile picture URL
    private String generateRandomProfilePicture() {
        // List of placeholder image URLs
        String[] profilePictures = {
                "C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\src\\main\\Img\\pic 1.jpg",
                "C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\src\\main\\Img\\pic 2.jpg",
                "C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\src\\main\\Img\\pic 3.jpg",
                "C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\src\\main\\Img\\pic 4.jpg",
                "C:\\Users\\yassi\\OneDrive\\Bureau\\ProjetPidev\\src\\main\\Img\\pic 5.jpg",
                // Add more placeholder image URLs as needed
        };
        // Generate a random index to select a profile picture URL
        int randomIndex = (int)(Math.random() * profilePictures.length);
        return profilePictures[randomIndex];
    }





}
