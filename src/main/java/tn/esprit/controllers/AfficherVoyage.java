    package tn.esprit.controllers;

    import java.io.IOException;
    import java.net.URL;
    import java.sql.SQLException;
    import java.util.ResourceBundle;
    import java.sql.Date;

    import tn.esprit.services.*;
    import javafx.event.ActionEvent;

    import javafx.beans.value.ObservableValue;
    import javafx.scene.control.TableColumn;
    import javafx.util.Callback;

    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ButtonType;
    import javafx.scene.control.TableView;
    import tn.esprit.entities.Groupe;
    import tn.esprit.entities.Voyage;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.collections.transformation.FilteredList;
    import javafx.scene.control.TextField;
    import javafx.beans.property.SimpleStringProperty;

    import java.io.FileOutputStream;
    import java.io.FileWriter;

    import org.apache.poi.ss.usermodel.Row;
    import org.apache.poi.xssf.usermodel.XSSFWorkbook;

    import org.apache.pdfbox.pdmodel.PDDocument;
    import org.apache.pdfbox.pdmodel.PDPage;
    import org.apache.pdfbox.pdmodel.PDPageContentStream;
    import org.apache.pdfbox.pdmodel.font.PDType1Font;
    public class AfficherVoyage {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<Voyage, String> ColArrivee;

        @FXML
        private TableColumn<Voyage, String> ColDepart;

        @FXML
        private TableColumn<Voyage, String> ColEvenementID;

        @FXML
        private TableColumn<Voyage, String> ColHebergementID;

        @FXML
        private TableColumn<Voyage, Integer> ColVID;

        @FXML
        private TableColumn<Groupe, Integer> ColGID;

        @FXML
        private TableColumn<Groupe, Integer> ColNumberMembre;

        @FXML
        private TableColumn<Voyage, Integer> ColVUtilisateurID;

        @FXML
        private TableColumn<Groupe, Integer> ColGUtilisateurID;

        @FXML
        private TableColumn<Voyage, String> ColVehiculeID;

        @FXML
        private TableColumn<Groupe, Integer> ColVoyageID;

        @FXML
        private TableView<Groupe> TableGroupe;

        @FXML
        private TableView<Voyage> TableVoyage;

        @FXML
        private TextField search;

        private FilteredList<Voyage> filteredList;

        public int x=0;

        @FXML
        private void exportToPDF(ActionEvent event) {
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Export to PDF");
            alert.setHeaderText("Exporting to PDF");
            alert.setContentText("Are you sure you want to export the table to PDF?");

            // Customize the buttons in the confirmation dialog
            ButtonType confirmButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No");

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Show and wait for user response
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == confirmButton) {
                    try {
                        exportToPDF(TableVoyage, "voyages.pdf");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @FXML
        private void exportToExcel(ActionEvent event) {
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Export to Excel");
            alert.setHeaderText("Exporting to Excel");
            alert.setContentText("Are you sure you want to export the table to Excel?");

            // Customize the buttons in the confirmation dialog
            ButtonType confirmButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No");

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Show and wait for user response
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == confirmButton) {
                    try {
                        exportToExcel(TableVoyage, "voyages.xlsx");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @FXML
        private void exportToNotepad(ActionEvent event) {
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Export to Notepad");
            alert.setHeaderText("Exporting to Notepad");
            alert.setContentText("Are you sure you want to export the table to Notepad?");

            // Customize the buttons in the confirmation dialog
            ButtonType confirmButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No");

            alert.getButtonTypes().setAll(confirmButton, cancelButton);

            // Show and wait for user response
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == confirmButton) {
                    try {
                        exportToTextFile(TableVoyage, "voyages.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        @FXML
        private void handleModifyVoyage(ActionEvent event) {
            Voyage selectedVoyage = TableVoyage.getSelectionModel().getSelectedItem();
            if (selectedVoyage != null) {
                // Perform action to open ModifyVoyage.fxml with selectedVoyage
                System.out.println("Modify Voyage: " + selectedVoyage);
            } else {
                showAlert("Aucun voyage sélectionné", "Veuillez sélectionner un voyage pour le modifier.");
            }
        }

        @FXML
        private void handleModifyGroupe(ActionEvent event) {
            Groupe selectedGroupe = TableGroupe.getSelectionModel().getSelectedItem();
            if (selectedGroupe != null) {
                // Perform action to open ModifyGroupe.fxml with selectedGroupe
                System.out.println("Modify Groupe: " + selectedGroupe);
            } else {
                showAlert("Aucun groupe sélectionné", "Veuillez sélectionner un groupe pour le modifier.");
            }
        }

        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }

        @FXML
        void AddVoyage(ActionEvent event) {
            try {
                // Load the FXML file for the AjouterVoyage scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterVoyage.fxml"));
                Parent root = loader.load();

                // Create a new stage for the AjouterVoyage scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Add Voyage");
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exceptions that may occur during loading or showing the scene
            }
        }

        @FXML
        private void modifyVoyage(ActionEvent event) {
            // Get the selected voyage from the TableView
            Voyage selectedVoyage = TableVoyage.getSelectionModel().getSelectedItem();
            if (selectedVoyage != null) {
                try {
                    // Load the FXML file for the ModifierVoyage scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVoyage.fxml"));
                    Parent root = loader.load();

                    // Get the controller of the ModifierVoyage.fxml file
                    ModifierVoyage controller = loader.getController();

                    // Call the initData method and pass the selected voyage and the TableView
                    controller.initData(selectedVoyage, TableVoyage);

                    // Create a new stage for the ModifierVoyage scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Modifier Voyage");
                    stage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                    // Handle any IOException that may occur during loading or showing the scene
                }
            } else {
                // If no voyage is selected, show an alert
                showAlert("Aucun voyage sélectionné", "Veuillez sélectionner un voyage à modifier.");
            }
        }


        @FXML
        void DeleteVoyage(ActionEvent event) {
            // Add logic to delete the selected voyage from the TableView
            Voyage selectedVoyage = TableVoyage.getSelectionModel().getSelectedItem();
            if (selectedVoyage != null) {
                // Show confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Voyage");
                alert.setContentText("Are you sure you want to delete the selected voyage?");

                // Customize the buttons in the confirmation dialog
                ButtonType confirmButton = new ButtonType("Yes");
                ButtonType cancelButton = new ButtonType("No");

                alert.getButtonTypes().setAll(confirmButton, cancelButton);

                // Show and wait for user response
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == confirmButton) {
                        // User clicked Yes, proceed with deletion
                        try {
                            // Delete the voyage from the database
                            VoyageService voyageService = new VoyageService();
                            voyageService.supprimer(selectedVoyage); // Pass the selected voyage object

                            // Remove the selected voyage from the underlying data source
                            TableVoyage.getItems().remove(selectedVoyage);
                        } catch (SQLException e) {
                            // Handle any SQL exception
                            e.printStackTrace(); // You might want to handle this more gracefully
                        }
                    }
                });
            }
        }




        @FXML
        void AddGroupe(ActionEvent event) {
            try {
                // Load the FXML file for the ajouterGroupe scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterGroupe.fxml"));
                Parent root = loader.load();

                // Create a new stage for the ajouterGroupe scene
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Add Groupe");
                stage.showAndWait();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exceptions that may occur during loading or showing the scene
            }
        }

        @FXML
        void ModifyGroupe(ActionEvent event) {
            // Add logic to open the appropriate scene for modifying a group
            Groupe selectedGroupe = TableGroupe.getSelectionModel().getSelectedItem();
            if (selectedGroupe != null) {
                try {
                    // Load the FXML file for the ModifierVoyage scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierGroupe.fxml"));
                    Parent root = loader.load();

                    // Create a new stage for the ModifierGroupe scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Modifier Groupe");
                    stage.showAndWait();
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle any exceptions that may occur during loading or showing the scene
                }
            }
        }

        @FXML
        void DeleteGroupe(ActionEvent event) throws SQLException {
            // Get the selected group from the TableView
            Groupe selectedGroupe = TableGroupe.getSelectionModel().getSelectedItem();
            if (selectedGroupe != null) {
                // Show confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Group");
                alert.setContentText("Are you sure you want to delete this group?");

                // Customize the buttons in the confirmation dialog
                ButtonType confirmButton = new ButtonType("Yes");
                ButtonType cancelButton = new ButtonType("No");

                alert.getButtonTypes().setAll(confirmButton, cancelButton);

                // Show and wait for user response
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == confirmButton) {
                        try {
                            // Delete the selected group from the database
                            GroupeService gs = new GroupeService();
                            gs.supprimer(selectedGroupe);

                            // Remove the selected group from the TableView
                            TableGroupe.getItems().remove(selectedGroupe);
                        } catch (SQLException e) {
                            e.printStackTrace(); // Handle any SQL exception
                        }
                    }
                });
            }
        }


        public void refresh(ActionEvent actionEvent) throws SQLException {
            ShowVoyages();
            setupSearchListener();
        }

        @FXML
        void initialize(){
            try {
                if(x==1){
                    ShowVoyages();
                    setupSearchListener();
                }
                if(x==2){
                    ShowGroupes();
                }
                //ShowGroupes();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



        private void setupSearchListener() {
            filteredList = new FilteredList<>(TableVoyage.getItems());

            search.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(voyage -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String searchText = newValue.toLowerCase();
                    return String.valueOf(voyage.getId()).toLowerCase().contains(searchText)
                            || getVehiculeName(voyage.getVehicule_id()).toLowerCase().contains(searchText)
                            || getHebergementName(voyage.getHebergement_id()).toLowerCase().contains(searchText)
                            || getEvenementName(voyage.getEvenement_id()).toLowerCase().contains(searchText)
                            || voyage.getDepart().toString().toLowerCase().contains(searchText)
                            || voyage.getArrivee().toString().toLowerCase().contains(searchText);
                });
            });

            TableVoyage.setItems(filteredList);
        }
        private String getEvenementName(int eventId) {
            // Use EvenementService to fetch the event name based on ID
            EvenementService evenementService = new EvenementService();
            return evenementService.getEvenementNameById(eventId);
        }

        private static String getHebergementName(int hebergementId) {
            // Use EvenementService to fetch the event name based on ID
            HebergementService hebergementService = new HebergementService();
            return hebergementService.getHebergementLieuById(hebergementId);
        }
        private static String getVehiculeName(int vehiculeId) {
            // Use EvenementService to fetch the event name based on ID
            VehiculeService vehiculeService = new VehiculeService();
            return vehiculeService.getTransportModelById(vehiculeId);
        }



        public void ShowVoyages() throws SQLException {
            VoyageService vs = new VoyageService();
            ObservableList<Voyage> list = vs.afficher();
            TableVoyage.setItems(list);
            ColDepart.setCellValueFactory(new PropertyValueFactory<>("depart"));
            ColArrivee.setCellValueFactory(new PropertyValueFactory<>("arrivee"));

            // Set the cell value factory for ColEvenementID to display event names
            ColEvenementID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Voyage, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Voyage, String> cellData) {
                    int eventId = cellData.getValue().getEvenement_id();
                    System.out.println("Event ID: " + eventId); // Log event ID
                    String eventName = ""; // Fetch event name based on ID
                    EvenementService evenementService = new EvenementService();
                    eventName = evenementService.getEvenementNameById(eventId);
                    return new SimpleStringProperty(eventName);
                }
            });

            // Set the cell value factory for ColVehiculeID to display transport model
            ColVehiculeID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Voyage, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Voyage, String> cellData) {
                    int vehiculeId = cellData.getValue().getVehicule_id();
                    System.out.println("Vehicule ID: " + vehiculeId); // Log vehicule ID
                    String transportModel = ""; // Fetch transport model based on ID
                    VehiculeService vehiculeService = new VehiculeService();
                    transportModel = vehiculeService.getTransportModelById(vehiculeId);
                    return new SimpleStringProperty(transportModel);
                }
            });

            // Set the cell value factory for ColHebergementID to display hebergement lieu
            ColHebergementID.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Voyage, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Voyage, String> cellData) {
                    int hebergementId = cellData.getValue().getHebergement_id();
                    System.out.println("Hebergement ID: " + hebergementId); // Log hebergement ID
                    String hebergementLieu = ""; // Fetch hebergement lieu based on ID
                    HebergementService hebergementService = new HebergementService();
                    hebergementLieu = hebergementService.getHebergementLieuById(hebergementId);
                    return new SimpleStringProperty(hebergementLieu);
                }
            });
        }


        public void ShowGroupes() throws SQLException {
            GroupeService gs = new GroupeService();
            ObservableList<Groupe> list = gs.afficher();
            TableGroupe.setItems(list);
            ColGID.setCellValueFactory(new PropertyValueFactory<>("id"));
            ColGUtilisateurID.setCellValueFactory(new PropertyValueFactory<>("utilisateur_id"));
            ColVoyageID.setCellValueFactory(new PropertyValueFactory<>("voyage_id"));
            ColNumberMembre.setCellValueFactory(new PropertyValueFactory<>("number_membre"));
        }




        @FXML
        private void openModifierVoyage(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVoyage.fxml"));
                Parent root = loader.load();
                ModifierVoyage controller = loader.getController();

                // Code to initialize the controller or pass any data if needed

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Modifier Voyage");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void refresh2(ActionEvent actionEvent) throws SQLException {
            ShowGroupes();
        }
        @FXML
        private void openChatbot(ActionEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChatBotUI.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("ChatBot");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @FXML
        private void openWeatherWidget(ActionEvent event) {
            try {
                // Load the FXML file for the weather widget
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/weather_widget.fxml"));
                Parent root = loader.load();

                // Create a new stage for the weather widget
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Weather Widget");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // PDF Export
        // PDF Export
        public static void exportToPDF(TableView<Voyage> tableView, String filePath) throws IOException {
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(100, 700);

                    // Write column headers
                    contentStream.showText("ID    Vehicule Name    Hebergement Name    Depart    Arrivee");
                    contentStream.newLine();

                    contentStream.newLineAtOffset(0, -20);

                    // Iterate through table items and write each row
                    ObservableList<Voyage> items = tableView.getItems();
                    for (Voyage voyage : items) {
                        String vehiculeName = getVehiculeName(voyage.getVehicule_id());
                        String hebergementName = getHebergementName(voyage.getHebergement_id());

                        String rowText = String.format("%-5s %-20s %-20s %-15s %-15s",
                                voyage.getId(), vehiculeName, hebergementName,
                                voyage.getDepart(), voyage.getArrivee());

                        contentStream.showText(rowText);
                        contentStream.newLine();
                        contentStream.newLineAtOffset(0, -20);
                    }

                    contentStream.endText();
                }

                document.save(filePath);
                System.out.println("PDF created successfully!");
            }
        }
        // Excel Export
        public static void exportToExcel(TableView<Voyage> tableView, String filePath) throws IOException {
            XSSFWorkbook workbook = new XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Voyages");

            // Create column headers
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Vehicule Name");
            headerRow.createCell(2).setCellValue("Hebergement Name");
            headerRow.createCell(3).setCellValue("Depart");
            headerRow.createCell(4).setCellValue("Arrivee");

            // Populate data rows
            ObservableList<Voyage> items = tableView.getItems();
            int rowCount = 1;
            for (Voyage voyage : items) {
                String vehiculeName = getVehiculeName(voyage.getVehicule_id());
                String hebergementName = getHebergementName(voyage.getHebergement_id());

                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(voyage.getId());
                row.createCell(1).setCellValue(vehiculeName);
                row.createCell(2).setCellValue(hebergementName);
                row.createCell(3).setCellValue(voyage.getDepart().toString());
                row.createCell(4).setCellValue(voyage.getArrivee().toString());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        }

        // Text File Export
        public static void exportToTextFile(TableView<Voyage> tableView, String filePath) throws IOException {
            FileWriter writer = new FileWriter(filePath);
            writer.write("ID    Vehicule Name    Hebergement Name    Depart    Arrivee\n");

            ObservableList<Voyage> items = tableView.getItems();
            for (Voyage voyage : items) {
                String vehiculeName = getVehiculeName(voyage.getVehicule_id());
                String hebergementName = getHebergementName(voyage.getHebergement_id());

                String rowText = String.format("%-5s %-20s %-20s %-15s %-15s",
                        voyage.getId(), vehiculeName, hebergementName,
                        voyage.getDepart(), voyage.getArrivee());

                writer.write(rowText + "\n");
            }
            writer.close();
        }


    }