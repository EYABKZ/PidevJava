package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import tn.esprit.entities.Post;
import tn.esprit.services.servicePost;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterPostControllers {

    servicePost sv = new servicePost();

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtViews_count;

    @FXML
    private TextField txtCreated_at;



    @FXML
    void ajouterAction(javafx.event.ActionEvent event) {
        try {
            String viewsCountText = txtViews_count.getText();
            if (!viewsCountText.isEmpty()) {
                int viewsCount = Integer.parseInt(viewsCountText);
                Post p = new Post(txtTitle.getText(), txtCreated_at.getText(), txtAuthor.getText(), viewsCount);

                int generatedId_Post = sv.ajouter(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("Post Ajouté avec succes");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));

                try {
                    Parent root = loader.load();
                    AfficherPostControllers afficherPControllers = loader.getController();

                    List<Post> allposts = sv.recuperer();
                    ObservableList<Post> items = FXCollections.observableArrayList(allposts);
                    afficherPControllers.AfficherList.setItems(items);

                    afficherPControllers.AfficherList.setCellFactory(param -> new ListCell<Post>() {
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

                    afficherPControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        Post selectedPost = newValue;

                        afficherPControllers.settxtTitle(selectedPost.getTitle());
                        afficherPControllers.settxtAuthor(selectedPost.getAuthor());
                        afficherPControllers.setTxtCreated_at(String.valueOf(selectedPost.getCreated_at()));
                        afficherPControllers.setTxtViews_Count(selectedPost.getViews_count());
                        afficherPControllers.setTxtId_Post(Integer.parseInt(String.valueOf(selectedPost.getId_Post())));
                    });

                    System.out.println("FXML loaded successfully.");

                    txtTitle.getScene().setRoot(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Handle case where text field is empty
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Views count cannot be empty");
                alert.show();
            }
        } catch (NumberFormatException e) {
            // Handle case where text field contains non-numeric characters
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid views count");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    public void settxtTitle(String txtTitle) { this.txtTitle.setText(txtTitle);
    }

    public void settxtAuthor(String txtAuthor) { this.txtAuthor.setText(txtAuthor);
    }

    public void setTxtCreated_at(String txtCreated_at) {
        this.txtCreated_at.setText(txtCreated_at);
    }


    public void setTxtViews_count(String txtViews_Count) { this.txtViews_count.setText(txtViews_Count);
    }
    }
