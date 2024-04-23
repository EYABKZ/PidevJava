package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Post;
import tn.esprit.services.servicePost;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ModifierPostController {

    servicePost sv = new servicePost();

    private Post postToUpdate;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtCreated_at;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtId_Post;
    @FXML
    private TextField txtViews_Count;

    public void setTxtTitle(String txtTitle) { this.txtTitle.setText(txtTitle);
    }

    public void setTxtAuthor(String txtAuthor) {
        this.txtAuthor.setText(txtAuthor);
    }

    public void setTxtCreated_at(String txtCreated_at) {this.txtCreated_at.setText(txtCreated_at);
    }

    public void setTxtId_Post(String txtId_Post) {
        this.txtId_Post.setText(txtId_Post);
    }
    public void setTxtViews_Count(String txtViews_Count) {
        this.txtViews_Count.setText(txtViews_Count);
    }




    @FXML
    void ModifierAction() {
        try {
            Post existingPost = new Post();


            existingPost.setTitle(txtTitle.getText());
            existingPost.setCreated_at(txtCreated_at.getText());
            existingPost.setAuthor(txtAuthor.getText());
            existingPost.setId_Post(Integer.parseInt(txtId_Post.getText()));
            existingPost.setViews_count(Integer.parseInt(txtViews_Count.getText()));

            // Call the modifier method with the modified POST object
            sv.modifier(existingPost);
            System.out.println("done");

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("POST modifié avec succès");
            alert.show();

            // Reload the view

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));
                Parent root = loader.load();

            AfficherPostControllers afficherPostControllers = loader.getController();
            List<Post> allPosts = sv.recuperer();

            // Add all transports to the ListView
            ObservableList<Post> items = FXCollections.observableArrayList(allPosts);
            afficherPostControllers.AfficherList.setItems(items);

            // Use a CellFactory to display the Transport_Model as the text of the list items
            afficherPostControllers.AfficherList.setCellFactory(param -> new ListCell<Post>() {
                @Override
                protected void updateItem(Post item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getTitle() == null) {
                        setText(null);
                    } else {
                        setText(item.getTitle());
                    }
                }
            });

            // Set an on-click listener for the ListView items
            afficherPostControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Fetch the details of the selected transport
                Post selectedPost= newValue;

                // Display the details of the selected transport
                afficherPostControllers.settxtTitle(selectedPost.getTitle());
                afficherPostControllers.settxtAuthor(selectedPost.getAuthor());
                afficherPostControllers.setTxtCreated_at(selectedPost.getCreated_at());
                afficherPostControllers.setTxtViews_Count(selectedPost.getViews_count());
                afficherPostControllers.setTxtId_Post(Integer.parseInt(String.valueOf(selectedPost.getId_Post())));
            });

            // Get the current stage from the event source
            Stage stage = (Stage) txtId_Post.getScene().getWindow();

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

    @FXML
    void SupprimerAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this Post?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                sv.supprimer(Integer.parseInt(txtId_Post.getText()));

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("Post supprimé avec succès");
                alert.show();

                // Reload the view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPost.fxml"));

                try {
                    Parent root = loader.load();

                    AjouterPostControllers a = loader.getController();
                    a.setTxtCreated_at("");
                    a.settxtTitle("");
                    a.settxtAuthor("");
                    a.setTxtViews_count("");

                    txtTitle.getScene().setRoot(root);

                } catch (IOException e) {
                    // Handle IO exception
                    e.printStackTrace();
                }
            }catch (SQLException e) {
                // Handle SQL exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(e.getMessage());
                alert.show();
            }
        }
    }

    public void setTextFields(Post R){
        txtId_Post.setText(String.valueOf(R.getId_Post()));
        txtTitle.setText(R.getTitle());
        txtAuthor.setText(String.valueOf(R.getAuthor()));
        txtCreated_at.setText(String.valueOf(R.getCreated_at()));
        txtViews_Count.setText(String.valueOf(R.getViews_count()));
    }}


