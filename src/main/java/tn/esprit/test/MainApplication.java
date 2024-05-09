package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherPost.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("file:///C:/Users/yassi/IdeaProjects/projet2/src/main/java/tn/esprit/css/fullpackstyling.css");
            primaryStage.setTitle("Ajouter Personne");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
