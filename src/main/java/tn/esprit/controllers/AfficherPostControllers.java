package tn.esprit.controllers;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.entities.Comment;
import tn.esprit.entities.Post;
import tn.esprit.entities.React;
import tn.esprit.services.serviceComment;
import tn.esprit.services.servicePost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Predicate;

public class AfficherPostControllers {
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";


    servicePost sv = new servicePost();
    serviceComment sv_comment = new serviceComment();
    private FilteredList<Post> filteredPosts;

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
    private GridPane postContainer;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchTextField;

    @FXML
    private ListView<Comment> commentairesListView;


    public ListView<Post> AfficherList;

    public void settxtTitle(String txtTitle) {
        this.txtTitle.setText(txtTitle);
    }

    public void setTxtCreated_at(String txtCreated_at) {
        this.txtCreated_at.setText(txtCreated_at);
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

    public void initialize() throws java.sql.SQLException {
        filteredPosts = new FilteredList<>(FXCollections.observableArrayList(sv.recuperer()), p -> true);

        // Ajoute un gestionnaire d'événements au champ de recherche pour détecter les changements de texte
        searchTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    searchWhenKeyPress(searchTextField.getText());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        try {
            scroll.prefWidth(ScrollPane.USE_COMPUTED_SIZE);

            // Get posts from service
            //ObservableList<Post> items = FXCollections.observableArrayList(sv.recuperer());
            showPosts(filteredPosts);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void showPosts(ObservableList<Post> posts) throws SQLException {

        int column = 0;
        int row = 0;
        int postsPerLine = 3;
        int horizontalGap = 20;
        int verticalGap = 20;

        // Set spacing between cells
        postContainer.setHgap(horizontalGap);
        postContainer.setVgap(verticalGap);
        for (Post post : posts) {
            VBox postBox = new VBox(10);
            postBox.setStyle("-fx-border-color: black; -fx-padding: 10px;");
// Load your icon image
            Image iconImage = new Image(getClass().getResource("/profile.png").toExternalForm());

// Create an ImageView with the icon image
            ImageView iconImageView = new ImageView(iconImage);

// Set the size of the icon (optional)
            iconImageView.setFitWidth(16); // Adjust the width as needed
            iconImageView.setFitHeight(16); // Adjust the height as needed
            // Add other elements for the post (title, author, etc.)
            Label authorLabel = new Label(post.getAuthor() + "  |  " + post.getCreated_at());
            // Set the icon as the graphic of the label
            authorLabel.setGraphic(iconImageView);
            Button modifier = new Button("");
            modifier.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        goToModification(event, post);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            Image iconModifier = new Image(getClass().getResource("/ponit.png").toExternalForm());

            ImageView iconmodifier = new ImageView(iconModifier);

// Set the size of the icon (optional)
            iconmodifier.setFitWidth(16); // Adjust the width as needed
            iconmodifier.setFitHeight(16); // Adjust the height as needed
            modifier.setGraphic(iconmodifier);
            modifier.setAlignment(Pos.TOP_LEFT);
            HBox container = new HBox(10);
            container.getChildren().addAll(authorLabel, modifier);
// Set the alignment of the graphic (optional)
            authorLabel.setContentDisplay(ContentDisplay.LEFT);
            Label contentLabel = new Label(post.getTitle());
            Button translateButton = new Button("translate");
            translateButton.setOnAction(event -> {
                // Create a dialog box for entering the reply
                try {
                    String translatedText = translate("en","fr",post.getTitle());
                    Label traslatedLabel = new Label();
                    traslatedLabel.setText(translatedText);
                    traslatedLabel.setAlignment(Pos.CENTER);
                    VBox translatedLayout = new VBox(traslatedLabel);
                    Scene translatedScene = new Scene(translatedLayout, 300, 30);
                    Stage translatedStage = new Stage();
                    translatedStage.setScene(translatedScene);
                    translatedStage.setTitle("traduction");
                    translatedStage.show();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            Image image = new Image(getClass().getResource("/images.jpg").toExternalForm());

            // Set up ImageView
            ImageView profileImageView = new ImageView(image);

            List<Comment> commentaires = commentService.recupererComPost(post.getId_Post());

            // Add comment box
            VBox commentBox = new VBox(8);
            //commentBox.setStyle("-fx-padding: 5px"); // Example styling
            for (Comment comment : commentaires) { // Assuming post.getComments() returns a list of comments
                if (comment.getParent_comment_id() == null){
                    VBox boxPerComment = new VBox(5);
                boxPerComment.setStyle("-fx-padding: 5px; -fx-background-color: #EEEEEE;"); // Example styling

                Label commentLabel = new Label(comment.getContent());
                Image iconImageComm = new Image(getClass().getResource("/profile.png").toExternalForm());

// Create an ImageView with the icon image
                ImageView iconImageViewComm = new ImageView(iconImage);

// Set the size of the icon (optional)
                iconImageViewComm.setFitWidth(10); // Adjust the width as needed
                iconImageViewComm.setFitHeight(10); // Adjust the height as needed
                // Add other elements for the post (title, author, etc.)
                Label authorComm = new Label(comment.getAuthorC());
                // Set the icon as the graphic of the label
                authorComm.setGraphic(iconImageViewComm);

                //Like & Dislike
                Label like_dislike = new Label(comment.getReacts_id().getlikes() + " likes " + comment.getReacts_id().getDislike() + " dislikes ");
                Image iconLike = new Image(getClass().getResource("/like.png").toExternalForm());
                Image iconDisLike = new Image(getClass().getResource("/dislike.png").toExternalForm());
                ImageView iconLikeView = new ImageView(iconLike);
                ImageView iconDisLikeView = new ImageView(iconDisLike);
                iconLikeView.setFitWidth(10); // Adjust the width as needed
                iconLikeView.setFitHeight(10); // Adjust the height as needed
                iconDisLikeView.setFitWidth(10); // Adjust the width as needed
                iconDisLikeView.setFitHeight(10); // Adjust the height as needed
                Button likeButton = new Button();
                Button dislikeButton = new Button();
                Button replyButton = new Button("répondre");
                likeButton.setGraphic(iconLikeView);
                dislikeButton.setGraphic(iconDisLikeView);
                likeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            likeComment(comment);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
                dislikeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            dislikeComment(comment);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }
                });

                    replyButton.setOnAction(event -> {
                        // Create a dialog box for entering the reply
                        TextField replyField = new TextField();
                        replyField.setPromptText("Enter your reply");
                        replyField.setOnAction(replyEvent -> {
                            // Execute the action when Enter is pressed
                            String reply = replyField.getText();
                            System.out.println("Reply: " + reply);
                            // Add your action here, such as sending the reply
                            // For demonstration, we're just printing the reply to the console
                            Stage stage = (Stage) replyField.getScene().getWindow();
                            // Call your function here with the reply as an argument
                            try {
                                closeDialog(comment,post.getId_Post(),replyField);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });

                        VBox dialogLayout = new VBox(replyField);
                        Scene dialogScene = new Scene(dialogLayout, 300, 50);
                        Stage dialogStage = new Stage();
                        dialogStage.setScene(dialogScene);
                        dialogStage.setTitle("Reply");
                        dialogStage.show();
                    });

                //  Image iconReply = new Image(getClass().getResource("/reply.png").toExternalForm());


                HBox reactBox = new HBox(5);
                reactBox.getChildren().addAll(likeButton, dislikeButton,replyButton);

                //Reply
                List<Comment> replies = commentService.recupererReply(comment.getId_Comment());

                    VBox boxReply = new VBox(4);
                    boxReply.setStyle("-fx-padding: 4px; -fx-background-color: #FFF;"); // Example styling
                    for(Comment reply:replies) {
                        VBox miniBox = new VBox(2);
                        miniBox.setStyle("-fx-padding: 2px; -fx-background-color: #EEEEEE;"); // Example styling

                        Label replyLabel = new Label(reply.getContent());
                        Image iconreply = new Image(getClass().getResource("/profile.png").toExternalForm());

// Create an ImageView with the icon image
                        ImageView iconImagereply = new ImageView(iconreply);

// Set the size of the icon (optional)
                        iconImagereply.setFitWidth(10); // Adjust the width as needed
                        iconImagereply.setFitHeight(10); // Adjust the height as needed
                        // Add other elements for the post (title, author, etc.)
                        Label authorReply = new Label(reply.getAuthorC());
                        // Set the icon as the graphic of the label
                        authorComm.setGraphic(iconImagereply);
                        miniBox.getChildren().addAll(authorReply,replyLabel);
                        miniBox.setAlignment(Pos.BOTTOM_LEFT);
                        boxReply.getChildren().add(miniBox);
                    }

                boxPerComment.getChildren().addAll(authorComm, commentLabel, like_dislike, reactBox);
                    if(!replies.isEmpty()){
                        boxPerComment.getChildren().add(boxReply);
                    }

                        commentBox.getChildren().add(boxPerComment);
            }

            }
            VBox boxPerComment = new VBox(5);
            boxPerComment.setStyle("-fx-padding: 5px"); // Example styling
            TextField newComment = new TextField("Taper votre message ici");
            Button ajoutComment = new Button("commenter");
            ajoutComment.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        ajouterCommentaire(post.getId_Post(), post.getAuthor(), newComment);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            boxPerComment.getChildren().addAll(newComment, ajoutComment);
            commentBox.getChildren().addAll(boxPerComment);
            postBox.getChildren().addAll(container, profileImageView, contentLabel,translateButton, commentBox);

            // Add postBox to GridPane at specified row and column
            postContainer.add(postBox, column, row);
            postContainer.setAlignment(Pos.CENTER);

            // Increment column
            column++;

            // If reached maximum number of columns, reset column and increment row
            if (column == postsPerLine) {
                column = 0;
                row++;
            }
        }
    }

    // Méthode pour gérer la recherche
    private void search(String searchText) throws SQLException {
        // Vérifie si le champ de recherche est vide
        if (searchText.isEmpty()) {
            // Si le champ de recherche est vide, affiche tous les posts

        } else {
            List<Post> searchResults = servicePost.search(searchText);
            // Met à jour l'interface utilisateur avec les résultats de la recherche
            displaySearchResults(searchResults);
        }
    }

    private void displayAllPosts() {
        // Implémentez cette méthode pour afficher tous les posts dans l'interface utilisateur
        // Vous pouvez utiliser votre code existant pour afficher les posts
    }

    // Méthode pour afficher les résultats de la recherche dans l'interface utilisateur
    private void displaySearchResults(List<Post> searchResults) {
        // Efface le contenu précédent du conteneur de posts
        postContainer.getChildren().clear();
        // Affiche les résultats de la recherche dans le conteneur de posts
        for (Post post : searchResults) {
            // Ajoutez le code pour afficher chaque post dans l'interface utilisateur
            // Utilisez votre code existant pour afficher les posts
        }
    }

    @FXML
    void ajouterPost(javafx.event.ActionEvent event) throws Exception {
        try {
            //String viewsCountText = txtViews_count.getText();
            if (txtTitle.getText().isEmpty() || txtAuthor.getText().isEmpty()) {
                // Afficher une alerte d'erreur si un champ est vide
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("All fields must be filled");
                alert.show();
            } else {
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Define a custom date and time format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Format the current date and time using the formatter
                String formattedDateTime = currentDateTime.format(formatter);
                // Créer un nouvel objet Post
                Post p = new Post(txtTitle.getText(), formattedDateTime, txtAuthor.getText(), 0);
                int generatedId_Post = sv.ajouter(p);

                // Ajouter le post (méthode sv.ajouter(p))

                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Post added successfully");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost .fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
                Stage stage = (Stage) AjouterButton.getScene().getWindow();

                // Changer la scène pour afficher la vue "AjouterPost.fxml"
                stage.setScene(scene);
                stage.show();
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


    public void setTxtId_Post(int txtId_Post) {
        this.txtId_Post.setText(String.valueOf(txtId_Post));
    }

    public void setTxtViews_Count(int txtViews_count) {
        this.txtViews_count.setText(String.valueOf(txtViews_count));
    }


    public void goToModification(javafx.event.ActionEvent event, Post post) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierP.fxml"));

        try {
            Parent root = loader.load();
            ModifierPostController modifierController = loader.getController();
            modifierController.setTxtCreated_at(post.getCreated_at());
            modifierController.setTxtTitle(post.getTitle());
            modifierController.setTxtAuthor(post.getAuthor());
            modifierController.setTxtId_Post(Integer.toString(post.getId_Post()));
            modifierController.setTxtViews_Count(Integer.toString(post.getViews_count()));


            // Debug statement to check if root is loaded successfully
            System.out.println("FXML loaded successfully.");

            txtTitle.getScene().setRoot(root);
        } catch (IOException e) {
            // Instead of just printing, handle the IOException
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterCommentaire(int post_id, String author, TextField content) throws SQLException {

        try {

            Post p = sv.recupererPost(post_id);
            System.out.println("hello");
            Comment c = new Comment(0, content.getText(), author, p, null, null);
            int generatedId = sv_comment.ajouter(c);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Succes");
            alert.setContentText("Post Ajouté avec succes");
            alert.show();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost .fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
                Stage stage = (Stage) AjouterButton.getScene().getWindow();

                // Changer la scène pour afficher la vue "AjouterPost.fxml"
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void likeComment(Comment comment) throws SQLException {

        try {
            React react=comment.getReacts_id();
            if(react.getUser_like() == null ||!react.getUser_like().contains(comment.getAuthorC()) )
            {
                if(react.getUser_dislike() != null && react.getUser_dislike().contains(comment.getAuthorC())){
                    react.setDislike(react.getDislike()-1);
                    react.setUser_dislike(react.getUser_dislike().replace(comment.getAuthorC()+";", ""));
                }
                react.setlikes(react.getlikes()+1);
                react.setUser_like(react.getUser_like()==null?comment.getAuthorC()+";":react.getUser_like().concat(comment.getAuthorC()+";"));

            }
            else{
                react.setlikes(react.getlikes()-1);
                react.setUser_like(react.getUser_like().replace(comment.getAuthorC()+";", ""));
            }
            sv_comment.modifierReact(react);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost .fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
                Stage stage = (Stage) AjouterButton.getScene().getWindow();

                // Changer la scène pour afficher la vue "AjouterPost.fxml"
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 @FXML
    void dislikeComment(Comment comment) throws SQLException {

        try {

            React react=comment.getReacts_id();
            if(react.getUser_dislike() == null || !react.getUser_dislike().contains(comment.getAuthorC()) )
            {
                if(react.getUser_like() != null && react.getUser_like().contains(comment.getAuthorC())){
                    react.setlikes(react.getlikes()-1);
                    react.setUser_like(react.getUser_like().replace(comment.getAuthorC()+";", ""));
                }
                react.setDislike(react.getDislike()+1);
                react.setUser_dislike(react.getUser_dislike()==null?comment.getAuthorC()+";":react.getUser_dislike().concat(comment.getAuthorC()+";"));
            } else{
                react.setDislike(react.getDislike()-1);
                react.setUser_dislike(react.getUser_dislike().replace(comment.getAuthorC()+";", ""));
            }
            sv_comment.modifierReact(react);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost .fxml"));
                Parent root = loader.load();

                // Créer une nouvelle scène avec la vue chargée
                Scene scene = new Scene(root);

                // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche
                Stage stage = (Stage) AjouterButton.getScene().getWindow();

                // Changer la scène pour afficher la vue "AjouterPost.fxml"
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void searchWhenKeyPress(String key) throws SQLException {

        Predicate<Post> postPredicate = post -> post.getTitle().toLowerCase().contains(key.toLowerCase());
        filteredPosts.setPredicate(postPredicate);


        // Clear existing posts in UI
        postContainer.getChildren().clear();
        // Show filtered posts
        showPosts(filteredPosts);
    }
    private void closeDialog(Comment comment,Integer post_id,TextField replyField) throws SQLException, IOException {
        Post p = sv.recupererPost(post_id);
        Comment c = new Comment(0, replyField.getText(), comment.getAuthorC(), p, comment, null);
        int generatedId = sv_comment.ajouter(c);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage1 = (Stage) replyField.getScene().getWindow();
        Stage stage = (Stage) AjouterButton.getScene().getWindow();

        try {
            stage1.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPost .fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir la scène actuelle à partir du bouton ou du nœud parent le plus proche

            // Changer la scène pour afficher la vue "AjouterPost.fxml"
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String  translate(String fromLang, String toLang, String text) throws Exception {
        // TODO: Should have used a 3rd party library to make a JSON string from an object
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        System.out.println("Status Code: " + statusCode);
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        StringBuilder result = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            result.append(output).append("\n"); // Append each line to the result with a newline character
        }
        conn.disconnect();
        return result.toString();
    }

}