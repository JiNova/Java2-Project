package gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ProjController {

 @FXML
 public TextField url;
 @FXML
 public TextField keyword;
 @FXML
 public CheckBox sentence;
 @FXML
 public TableView<Sentence>table;
@FXML
 public void search(ActionEvent e) {

  String key = keyword.getText();

  String urlField = url.getText();

  // if(urlField.contains(key)){
  System.out.println("TEXT: " + urlField + ",KEY: " + key);
  // }
  ObservableList<Sentence> sentences = table.getItems();
  sentences.add(new Sentence (url.getText(),key,""));

  url.clear();
  keyword.clear();
 }
@FXML
 public void save(ActionEvent e){
 File file = new File("java.txt");
  try {
   BufferedWriter output = new BufferedWriter(new FileWriter(file));
   int i = 1 ;
   for(Sentence s : table.getItems()){
     output.write(i+". Sentence: "+s.getSentence()+ " Preceeding Tag : "+s.getPreTag()+" Following Tag: "+s.getfTag());
     i++;
    System.out.println(i+". Sentence: "+s.getSentence()+ " Preceeding Tag : "+s.getPreTag()+" Following Tag: "+s.getfTag());
   }
   output.close();
   System.out.println("Finished Writing");
  }
  catch ( Exception ex){
   ex.printStackTrace();
  }
 }

 @FXML
 public void clean(ActionEvent e){

  table.getItems().clear();
  url.clear();
  keyword.clear();
 }
}
