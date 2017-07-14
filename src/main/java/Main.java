package proj;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

 public void start(Stage primaryStage) throws IOException {
  Parent root;
 
    try {

     root = FXMLLoader.load(getClass().getClassLoader().getResource("JavaProj.fxml"));
     Scene scene1 = new Scene(root);
     scene1.setRoot(root);
     primaryStage.setScene(scene1);
     primaryStage.sizeToScene();
     primaryStage.setTitle("KWIC");
     primaryStage.show();
    } catch (IOException e) {
     e.printStackTrace();
     return;
    }

   }


 public static void main(String[] args) {

  launch(args);
 }

}
