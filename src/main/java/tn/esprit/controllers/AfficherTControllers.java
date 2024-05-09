package tn.esprit.controllers;


import com.gluonhq.maps.MapLayer;
import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;

import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.image.Image;

import com.mysql.cj.result.Row;

import javafx.scene.Node;

import tn.esprit.entities.Moy_Transport;
import tn.esprit.services.ServiceMoy_Transport;

import java.util.DoubleSummaryStatistics;


public class AfficherTControllers {

    public ListView <Moy_Transport> AfficherList;

    @FXML
    private TextField txtId;

    @FXML
    private ImageView Transport_Picture = new ImageView();

    @FXML
    private TextField txtTransport_Model;

    @FXML
    private TextField txtTransport_Price;

    @FXML
    private TextField txtTransport_Description;

    @FXML
    private TextField txtDisponibility;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;


    @FXML
    private TableView<Moy_Transport> tableviewVoiture;

    @FXML
    private TableColumn<Moy_Transport, String> colMarque;

    @FXML
    private TableColumn<Moy_Transport, LocalDate> colAnnee;

    @FXML
    private TableColumn<Moy_Transport, Integer> colPrix_j;

    @FXML
    private TableColumn<Moy_Transport, Integer> colKilometrage;

    @FXML
    private TableColumn<Moy_Transport, Integer> colNbrPlaces;

    private ServiceMoy_Transport voitureService;


    public void setTransport_Picture(String Transport_Picture) {
        Image image = new Image(Transport_Picture); // Create an Image from the URL
        this.Transport_Picture.setImage(image); // Set the Image to the ImageView
    }

    public void settxtTransport_Model(String txtTransport_Model) {
        this.txtTransport_Model.setText(txtTransport_Model);
    }

    public void settxtTransport_Price(String txtTransport_Price) {
        this.txtTransport_Price.setText(txtTransport_Price);
    }

    public void settxtTransport_Description(String txtTransport_Description) {
        this.txtTransport_Description.setText(txtTransport_Description);
    }

    public void settxtDisponibility(String TxtDisponibility) {
        this.txtDisponibility.setText(TxtDisponibility);
    }

    public void settxtId(String txtId) {
        this.txtId.setText(txtId);
    }

    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTM.fxml"));

        try {
            Parent root = loader.load();
            ModifierTransportController modifierController = loader.getController();
            modifierController.setTxtTransport_Description(txtTransport_Description.getText());
            modifierController.setTxtTransport_Model(txtTransport_Model.getText());
            modifierController.setTxtTransport_Price(txtTransport_Price.getText());
            String imageUrl = Transport_Picture.getImage().getUrl();
            modifierController.setTransport_Picture(imageUrl);
            modifierController.setTxtDisponibility(txtDisponibility.getText());
            modifierController.setTxtId(txtId.getText());

            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            // Get the current stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }


    public void initialize() {
        voitureService = new ServiceMoy_Transport();
        setupTableView();
        loadVoitures();

        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Appelle la méthode performSearch à chaque fois que le texte change
            performSearch(newValue.toLowerCase());
        });
        MapView mapView = createMapView();
        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }

    private void performSearch(String searchString) {
        tableviewVoiture.getItems().clear(); // Clear previous items

        try {
            if (searchString.isEmpty()) {
                // Si le champ de recherche est vide, recharge toutes les voitures
                loadVoitures();
            } else {
                // Filtrer les voitures en fonction du texte de recherche
                List<Moy_Transport> filteredVoitures = voitureService.recuperer().stream()
                        .filter(voiture -> voiture.getTransport_Model().toLowerCase().contains(searchString))
                        .collect(Collectors.toList());

                // Ajouter les voitures filtrées à la table
                tableviewVoiture.getItems().addAll(filteredVoitures);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception SQLException
            // Optionnellement, afficher un message d'erreur à l'utilisateur
        }
    }

    private void setupTableView() {
        colMarque.setCellValueFactory(new PropertyValueFactory<>("Transport_Model"));
        colAnnee.setCellValueFactory(new PropertyValueFactory<>("Transport_Price"));
        colPrix_j.setCellValueFactory(new PropertyValueFactory<>("Transport_Description"));
        colKilometrage.setCellValueFactory(new PropertyValueFactory<>("Disponibility"));
        colNbrPlaces.setCellValueFactory(new PropertyValueFactory<>("Transport_Picture"));
    }



    private void loadVoitures() {
        try {
            List<Moy_Transport> voitures = voitureService.recuperer();
            tableviewVoiture.getItems().addAll(voitures);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur
        }
    }




    @FXML
    public void handleRowSelection(javafx.event.ActionEvent event) {
        Moy_Transport selectedVoiture = tableviewVoiture.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
            deleteButton.setDisable(false); // Enable delete button
            updateButton.setDisable(false); // Enable update button
        } else {
            deleteButton.setDisable(true); // Disable delete button if no row is selected
            updateButton.setDisable(true); // Disable update button if no row is selected
        }
    }


    @FXML
    public void handleDeleteButton(javafx.event.ActionEvent event) {
        Moy_Transport selectedVoiture = tableviewVoiture.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
            try {
                // Call your delete method with selectedVoiture.getId() as parameter
                voitureService.supprimer(selectedVoiture.getId_transport());
                // Remove the selected row from the TableView
                tableviewVoiture.getItems().remove(selectedVoiture);
            } catch (SQLException e) {
                e.printStackTrace(); // Handle error
            }
        }
    }


    public void handleUpdateButton(javafx.event.ActionEvent event) {
        Moy_Transport selectedVoiture = tableviewVoiture.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
            try {
                // Load the ModifierVoiture.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTM.fxml"));
                Parent root = loader.load();

                // Get the controller for ModifierV.fxml
                ModifierTransportController modifierController = loader.getController();

                // Pass the selected voiture to the controller to pre-fill fields
                modifierController.setTextFields(selectedVoiture);
                // Get the current stage from the event source
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // Create a new stage

                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Transport");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); // Handle error loading FXML
            }

        }
    }

    @FXML
    private void handleSortButtonClick() {
        // Call a method to sort the table
        sortTable();
    }

    private void sortTable() {
        // Perform sorting logic here
        // For example, to sort by the Marque column in ascending order
        colMarque.setSortType(TableColumn.SortType.ASCENDING);
        tableviewVoiture.getSortOrder().clear();
        tableviewVoiture.getSortOrder().add(colMarque);
        tableviewVoiture.sort();
    }

    @FXML
    private void showStatistics() {
        ObservableList<Moy_Transport> voitures = tableviewVoiture.getItems();
        if (!voitures.isEmpty()) {
            // Calculate statistics
            DoubleSummaryStatistics stats = voitures.stream()
                    .mapToDouble(voiture -> voiture.getTransport_Price())
                    .summaryStatistics();

            // Display statistics in a bar chart
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>("Min", stats.getMin()));
            series.getData().add(new XYChart.Data<>("Max", stats.getMax()));
            series.getData().add(new XYChart.Data<>("Average", stats.getAverage()));
            barChart.getData().add(series);

            // Create and show an alert dialog containing the bar chart
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Statistics");
            alert.setHeaderText(null);
            alert.getDialogPane().setContent(barChart);
            alert.showAndWait();
        } else {
            // No data to calculate statistics
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Data");
            alert.setHeaderText(null);
            alert.setContentText("No voiture data available.");
            alert.showAndWait();
        }
    }

    public void handleRowSelection(javafx.scene.input.MouseEvent mouseEvent) {
        Moy_Transport selectedVoiture = tableviewVoiture.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
            deleteButton.setDisable(false); // Enable delete button
            updateButton.setDisable(false); // Enable update button
        } else {
            deleteButton.setDisable(true); // Disable delete button if no row is selected
            updateButton.setDisable(true); // Disable update button if no row is selected
        }
    }

    @FXML
    private void exportToExcel(ActionEvent event) {
        // Get the data from the TableView
        ObservableList<Moy_Transport> data = tableviewVoiture.getItems();

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Transport Data");

        // Create header row
        org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Transport_Model");
        headerRow.createCell(1).setCellValue("Transport_Price");
        headerRow.createCell(2).setCellValue("Transport_Description");
        headerRow.createCell(3).setCellValue("Disponibility");
        headerRow.createCell(4).setCellValue("Transport_Picture");

        // Populate data rows
        int rowNum = 1;
        for (Moy_Transport voiture : data) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(voiture.getTransport_Model());
            row.createCell(1).setCellValue(voiture.getTransport_Price());
            row.createCell(2).setCellValue(voiture.getTransport_Price());
            row.createCell(3).setCellValue(voiture.getTransport_Description());
            row.createCell(4).setCellValue(voiture.getTransport_Picture());
        }

        // Choose file location
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "D:/Admin/Download/Transport.xlsx"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // Write the workbook content to the chosen file
            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                System.out.println("Excel file exported successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Open the exported file
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private final MapPoint guarage = new MapPoint(36.9002385, 10.186814);

    @FXML
    private VBox address;

    @FXML
    private ImageView qrCodeImageView = new ImageView();


    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(400, 300); // Adjust the preferred size to make the map smaller
        mapView.addLayer(new CustomMapLayer());
        mapView.setZoom(15);
        mapView.flyTo(0, guarage, 0.1);
        return mapView;
    }

    private class CustomMapLayer extends MapLayer {

        private final Node marker;

        public CustomMapLayer() {
            marker = new Circle(5, Color.RED);
            getChildren().add(marker);

        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(guarage.getLatitude(), guarage.getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }

    @FXML
    private void generateQRCode(ActionEvent event) {
        Moy_Transport selectedVoiture = tableviewVoiture.getSelectionModel().getSelectedItem();
        if (selectedVoiture != null) {
            String message = String.format("Model: %s\nPrix: %s\nDescription: %s\nDisponibility: %s\nPicture: %s",
                    selectedVoiture.getTransport_Model(), selectedVoiture.getTransport_Price(), selectedVoiture.getTransport_Description(),
                    selectedVoiture.getDisponibility(), selectedVoiture.getTransport_Picture());

            try {
                BufferedImage qrCodeImage = generateQRCode(message);
                Image qrCodeFXImage = SwingFXUtils.toFXImage(qrCodeImage, null);
                qrCodeImageView.setImage(qrCodeFXImage);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No car selected.");
        }
    }

    @FXML
    private BufferedImage generateQRCode(String message) throws WriterException {
        int width = 300;
        int height = 300;
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(message, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrCodeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrCodeImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return qrCodeImage;
    }

    private void loadUserAndGenerateQR(String marque) {
        String url = "jdbc:mysql://localhost:3306/travelwithme";
        String username = "root";
        String password = "";
        String query = "SELECT Transport_Model, Transport_Price, Transport_Description, Disponibility, Transport_Picture FROM Moy_Transport WHERE Transport_Model = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

             statement.setString(1, marque);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String Transport_Model = resultSet.getString("Transport_Model");
                    String Transport_Price = resultSet.getString("Transport_Price");
                    String Transport_Description = resultSet.getString("Transport_Description");
                    String Disponibility = resultSet.getString("Disponibility");
                    String Transport_Picture = resultSet.getString("Transport_Picture");

                    String userInfo = String.format("Model: %s\nPrix: %s\nDescription: %s\nDisponibility: %s\nPicture: %s",
                            Transport_Model, Transport_Price, Transport_Description, Disponibility, Transport_Picture);

                    generateQRCode(userInfo);
                } else {
                    System.out.println("Transport not found");
                }
            }
        } catch (SQLException | WriterException e) {
            e.printStackTrace();
        }
    }


}