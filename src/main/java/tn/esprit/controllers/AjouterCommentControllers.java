package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.services.serviceComment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AjouterCommentControllers {

    serviceComment sv = new serviceComment();

    @FXML
    private TextField txtAuthorC;

    @FXML
    private TextField txtContent;

    @FXML
    private TextField txtReplies_Count;
    @FXML
    private TextField txtPost_id;

    @FXML
    private Button AfficherButton;
    @FXML
    void ajouterAction(javafx.event.ActionEvent event) throws SQLException {

        try {

            Post p=sv.recupererPost(Integer.parseInt(txtPost_id.getText()));
            System.out.println("hello");
            Comment c=new Comment(Integer.parseInt(txtReplies_Count.getText()), txtContent.getText(), txtAuthorC.getText(),p,null,null);
            int generatedId = sv.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
            alert.setTitle("Succes");
            alert.setContentText("Post Ajouté avec succes");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherC.fxml"));
            try {
                Parent root = loader.load();
                AfficherCommentControllers afficherCControllers = loader.getController();

                List<Comment> allcomments = sv.recuperer();
                ObservableList<Comment> items = FXCollections.observableArrayList(allcomments);
                afficherCControllers.AfficherList.setItems(items);

                afficherCControllers.AfficherList.setCellFactory(param -> new ListCell<Comment>() {
                    @Override
                    protected void updateItem(Comment item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || item == null || item.getContent() == null) {
                            setText(null);
                        } else {
                            setText(item.getContent());
                        }
                    }
                });

                afficherCControllers.AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    Comment selectedComment = newValue;
                    afficherCControllers.setTxtId_Comment(String.valueOf(selectedComment.getId_Comment()));
                    afficherCControllers.setTxtContent(selectedComment.getContent());
                    afficherCControllers.settxtAuthorC(selectedComment.getAuthorC());
                    afficherCControllers.setTxtReplies_count(String.valueOf(selectedComment.getReplies_count()));
                });

                System.out.println("FXML loaded successfully.");

                txtContent.getScene().setRoot(root);
            } catch (Exception e) {
e.printStackTrace();
            }
        }catch (Exception e) {
e.printStackTrace();
        }
    }

    public void AfficherPost(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherP.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
            Stage stage = (Stage) AfficherButton.getScene().getWindow();

            // Changer la scène pour afficher la vue "AjouterPost.fxml"
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   // public void setTxtReplies_Count(int txtRepliesCount) { this.txtReplies_Count.setText(String.valueOf((txtReplies_Count)));
    //}

    public void settxtAuthorC(String txtAuthorC) { this.txtAuthorC.setText(txtAuthorC);
    }

    public void settxtContent(String txtContent) {
        this.txtContent.setText(txtContent);
    }

    public void setTxtPost_id(String txtPost_id) {
        this.txtPost_id.setText(txtPost_id);
    }

    public void setTxtReplies_Count(String replies_count) {
        this.txtReplies_Count.setText(replies_count);
    }

}

