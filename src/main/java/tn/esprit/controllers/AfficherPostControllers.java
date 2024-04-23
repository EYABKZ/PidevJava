package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import tn.esprit.entities.Post;

import java.io.IOException;

public class AfficherPostControllers {

    @FXML
    private TextField txtId_Post;

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtCreated_at;

    @FXML
    private TextField txtAuthor;
    @FXML
    private TextField txtViews_count;
    public ListView<Post> AfficherList;
    public  void settxtTitle(String txtTitle) { this.txtTitle.setText(txtTitle);
    }

    public  void setTxtCreated_at(String txtCreated_at) { this.txtCreated_at.setText(txtCreated_at);
    }

    public void settxtAuthor(String txtAuthor) {
        this.txtAuthor.setText(txtAuthor);
    }

    public void setTxtId_Post(int txtId_Post) { this.txtId_Post.setText(String.valueOf(txtId_Post)); }
    public void setTxtViews_Count(int txtViews_count) { this.txtViews_count.setText(String.valueOf(txtViews_count)); }


    public void goToModification(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierP.fxml"));

        try {
            Parent root = loader.load();
            ModifierPostController modifierController = loader.getController();
            modifierController.setTxtCreated_at(txtCreated_at.getText());
            modifierController.setTxtTitle(txtTitle.getText());
            modifierController.setTxtAuthor(txtAuthor.getText());
            modifierController.setTxtId_Post(txtId_Post.getText());
            modifierController.setTxtViews_Count(txtViews_count.getText());


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            txtTitle.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }
}
