package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.services.serviceComment;
import tn.esprit.services.servicePost;

import java.io.IOException;
import java.util.List;

public class AfficherPostControllers {


    servicePost sv = new servicePost();

    serviceComment commentService = new serviceComment();
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

    @FXML
    private Button AjouterButton;
    @FXML
    private Button AjouterButton11;

    @FXML
    private ListView<Comment> commentairesListView;
    public ListView<Post> AfficherList;
    public  void settxtTitle(String txtTitle) { this.txtTitle.setText(txtTitle);
    }

    public  void setTxtCreated_at(String txtCreated_at) { this.txtCreated_at.setText(txtCreated_at);
    }

    public void settxtAuthor(String txtAuthor) {
        this.txtAuthor.setText(txtAuthor);
    }
    public void ajouterAction(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPost.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
            Stage stage = (Stage) AjouterButton.getScene().getWindow();

            // Changer la scène pour afficher la vue "AjouterPost.fxml"
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ajouterCommentaire() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterComment.fxml"));

        try {
            Parent root = loader.load();
            AjouterCommentControllers ajoutController = loader.getController();
            ajoutController.setTxtPost_id(txtId_Post.getText());


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            txtTitle.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

    public void initialize() throws java.sql.SQLException{
        try {
            // Get posts from service
            ObservableList<Post> items = FXCollections.observableArrayList(sv.recuperer());

            AfficherList.setItems(items);

            // Set cell factory
            AfficherList.setCellFactory(param -> new ListCell<Post>() {
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

            // Handle selection change
            AfficherList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
              try{
                if (newValue != null) {
                    // Display selected post details
                    txtId_Post.setText(String.valueOf(newValue.getId_Post()));
                    txtTitle.setText(newValue.getTitle());
                    txtAuthor.setText(newValue.getAuthor());
                    txtCreated_at.setText(String.valueOf(newValue.getCreated_at()));
                    txtViews_count.setText(String.valueOf(newValue.getViews_count()));
                    // Récupérer la liste des commentaires associés à ce post
                    List<Comment> commentaires = commentService.recupererComPost(newValue.getId_Post());

                    // Afficher la liste des commentaires dans le ListView
                    ObservableList<Comment> items_comment = FXCollections.observableArrayList(commentaires);
                    commentairesListView.setItems(items_comment);
                }}catch (java.sql.SQLException e){e.printStackTrace();}
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
