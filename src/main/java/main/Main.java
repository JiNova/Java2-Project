package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
/**
Loads the GUI from FXML file 
*/
    public void start(Stage primaryStage) throws IOException {
        Parent root;

        try {

            root = FXMLLoader.load(getClass().getClassLoader().getResource("SearchAndEnjoy.fxml"));
            Scene scene1 = new Scene(root);
            scene1.setRoot(root);
            primaryStage.setScene(scene1);
            primaryStage.getIcons().add(new Image("file:icon.png"));
            primaryStage.sizeToScene();
            primaryStage.setTitle("Search and Enjoy");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
/**
Is used just as main method doesnt do anything else
*/
    public static void main(String[] args) {

        launch(args);
    }

}
