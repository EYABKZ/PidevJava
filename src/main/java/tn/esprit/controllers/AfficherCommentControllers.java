package tn.esprit.controllers;

import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.entities.Comment;

import java.io.IOException;

public class AfficherCommentControllers {

    public ListView<Comment> AfficherList;
    @FXML
    private TextField txtId_Comment;

    @FXML
    private TextField txtReplies_count;

    @FXML
    private TextField txtContent;

    @FXML
    private TextField txtAuthorC;

    public  void setTxtReplies_count(String txtRepliesCount) { this.txtReplies_count.setText(txtRepliesCount);
    }

    public  void setTxtContent(String txtContent) { this.txtContent.setText(txtContent);
    }

    public void settxtAuthorC(String txtAuthorC) {
        this.txtAuthorC.setText(txtAuthorC);
    }

    public void setTxtId_Comment(String txtId_Comment) { this.txtId_Comment.setText(txtId_Comment); }

    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierC.fxml"));

        try {
            Parent root = loader.load();
            ModifierCommentController modifierController = loader.getController();

            modifierController.setTxtContent(txtContent.getText());
            modifierController.setTxtReplies_count(txtReplies_count.getText());
            modifierController.setTxtAuthorC(txtAuthorC.getText());
            modifierController.setTxtIdC(txtId_Comment.getText());


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            txtContent.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }




}
