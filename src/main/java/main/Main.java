package main;

/**
 * Created by Julia on 11.07.2017
 */

import backend.CacheManager;
import backend.Parser;
import gui.util.GUIUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Parser parser = new Parser();

    public static Parser getParser() {

        return parser;
    }

    /**
     Loads the GUI from FXML file and sets up parser
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

            parser.setTagger("en-pos-maxent.bin");
            parser.setTokenizer("en-token.bin");
            parser.setLemmatizer("en-lemmatizer.bin");
            parser.setSplitter("en-sent.bin");

            CacheManager.cleanCache();

            primaryStage.show();

        } catch (IOException e) {

            GUIUtil.showAlert(Alert.AlertType.ERROR, "Could not load a critical file! The program will now exit.", e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Launches the actual JavaFX-Scene
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }

}
