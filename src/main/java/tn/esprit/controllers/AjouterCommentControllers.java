package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.entities.Comment;
import tn.esprit.services.serviceComment;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterCommentControllers {

    serviceComment sv = new serviceComment();

    @FXML
    private TextField txtAuthorC;

    @FXML
    private TextField txtContent;

    @FXML
    private TextField txtReplies_Count;

    @FXML
    void ajouterAction(javafx.event.ActionEvent event) {

        try {
            Comment c=new Comment(txtReplies_Count.getText(), txtContent.getText(), txtAuthorC.getText());
            int generatedId = sv.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Succes");
            alert.setContentText("Commentaire Ajouté avec succes");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherC.fxml"));

            try {
                Parent root = loader.load();
                AfficherCommentControllers afficherCommentControllers = loader.getController();
                afficherCommentControllers.setTxtReplies_count(txtReplies_Count.getText());
                afficherCommentControllers.setTxtContent(txtContent.getText());
                afficherCommentControllers.settxtAuthorC(txtAuthorC.getText());
                afficherCommentControllers.setTxtId_Comment(String.valueOf(generatedId));


                txtAuthorC.getScene().setRoot(root);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.show();

        }
    }

    public void setTxtReplies_Count(String txtRepliesCount) { this.txtReplies_Count.setText(String.valueOf(txtReplies_Count));
    }

    public void settxtAuthorC(String txtAuthorC) { this.txtAuthorC.setText(txtAuthorC);
    }

    public void settxtContent(String txtContent) {
        this.txtContent.setText(txtContent);
    }

}

