package gui;

import backend.Searcher;
import backend.datatype.SearchResult;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    public TextField url;
    @FXML
    public TextField keyword;
    @FXML
    public CheckBox sentence;
    @FXML
    public TableView<Sentence> table;
    @FXML
    public FlowPane miscButtons;

    @FXML
    public void search(ActionEvent e) {

        String key = keyword.getText();
        String urlField = url.getText();

        if (Pattern.compile("[^0-9A-Za-z]").matcher(key).find())
        {
            System.out.println("lel");
        }

        // if(urlField.contains(key)){
        // }

        ArrayList<SearchResult> results = new ArrayList<SearchResult>();

        try
        {
            results = Searcher.searchForTarget(key);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
            return;
        }

        ObservableList<Sentence> sentences = table.getItems();

        if (sentences.size() > 0)
        {
            sentences.clear();
        }

        for (int i = 0; i < results.size(); i ++)
        {
            sentences.add(new Sentence((i+1), results.get(i)));
        }

        miscButtons.setVisible(true);
    }

    @FXML
    public void save(ActionEvent e) {
//        File file = new File("java.txt");
//        try {
//            BufferedWriter output = new BufferedWriter(new FileWriter(file));
//            int i = 1;
//            for (Sentence s : table.getItems()) {
//                output.write(i + ". Sentence: " + s.getSentence() + " Preceeding Tag : " + s.getPreTag() + " Following Tag: " + s.getFolTag());
//                i++;
//                System.out.println(i + ". Sentence: " + s.getSentence() + " Preceeding Tag : " + s.getPreTag() + " Following Tag: " + s.getFolTag());
//            }
//            output.close();
//            System.out.println("Finished Writing");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    @FXML
    public void clean(ActionEvent e) {

        System.out.println("Cleaning UI...");
        table.getItems().clear();
        url.clear();
        keyword.clear();
        miscButtons.setVisible(false);
    }
}
