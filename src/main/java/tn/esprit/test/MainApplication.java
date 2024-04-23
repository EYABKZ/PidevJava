package tn.esprit.test;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import tn.esprit.controllers.AfficherPostControllers;
import tn.esprit.entities.Post;
import tn.esprit.services.servicePost;

import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    servicePost sv = new servicePost();
    @Override
    public void start(Stage primaryStage)  throws  Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherP.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Afficher Post");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
