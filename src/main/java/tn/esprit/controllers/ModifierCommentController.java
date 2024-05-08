package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Comment;
import tn.esprit.services.serviceComment;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class ModifierCommentController {

    serviceComment sv = new serviceComment();

    private Comment commentToUpdate;

    @FXML
    private TextField txtReplies_count;

    @FXML
    private TextField txtContent;

    @FXML
    private TextField txtAuthorC;

    @FXML
    private TextField txtId_comment;


    public void setTxtReplies_count(String txtRepliesCount) { this.txtReplies_count.setText(txtRepliesCount);
    }

    public void setTxtAuthorC(String txtAuthorC) {
        this.txtAuthorC.setText(txtAuthorC);
    }

    public void setTxtContent(String txtContent) {
        this.txtContent.setText(txtContent);
    }

    public void setTxtIdC(String txtId_comment) {
        this.txtId_comment.setText(txtId_comment);
    }


    @FXML
    void ModifierAction() {
        try {
            Comment existingComment = new Comment();

            existingComment.setReplies_count(Integer.parseInt(txtReplies_count.getText()));
            existingComment.setContent(txtContent.getText());
            existingComment.setAuthorC(txtAuthorC.getText());
            existingComment.setId_Comment(Integer.parseInt(txtId_comment.getText()));

            // Call the modifier method with the modified POST object
            sv.modifier(existingComment);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("Commentaire modifié avec succès");
            alert.show();

            // Reload the view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherC.fxml"));

            try {
                Parent root = loader.load();

                AjouterCommentControllers a = loader.getController();
                a.settxtContent("");
                a.setTxtReplies_Count("");
                a.settxtAuthorC("");

                txtContent.getScene().setRoot(root);

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

    @FXML
    void SupprimerAction() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this comment ?");
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {

                sv.supprimer(Integer.parseInt(txtId_comment.getText()));

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("Commentaire supprimé avec succès");
                alert.show();

                // Reload the view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterC.fxml"));

                try {
                    Parent root = loader.load();

                    AjouterCommentControllers a = loader.getController();
                    a.settxtContent("");
                    a.setTxtReplies_Count("");
                    a.settxtAuthorC("");

                    txtReplies_count.getScene().setRoot(root);

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


    }



