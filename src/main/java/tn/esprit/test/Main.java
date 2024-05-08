package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        //-------------------ADMIN-------------


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));

        //---------------------------CLIENT---------------------
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherFront.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

