package gui;

import backend.Searcher;
import backend.datatype.SearchResult;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    private static final int SPLIT_NUMBER = 2;
    private static final int TARGET_WORD_NUMBER = 3;
    @FXML
    private TextField url;
    @FXML
    private TextField keyword;
    @FXML
    private TableView<Sentence> table;
    @FXML
    private TableColumn<Sentence, String> sentenceColumn;
    @FXML
    private FlowPane miscButtons;

    @FXML
    private void initialize() {

        sentenceColumn.setCellFactory(sentenceColumn -> new TableCell<Sentence, String>() {

            @Override
            protected void updateItem(final String sentence, final boolean empty) {

                if (sentence == null || empty) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                } else {
                    setGraphic(null);

                    Pattern targetSentencePattern = Pattern.compile("(.*?)(%([^%\\s]*)%)(.*?)");
                    Matcher targetWordMatcher = targetSentencePattern.matcher(sentence);

                    if (targetWordMatcher.find()) {
                        String targetWord = targetWordMatcher.group(TARGET_WORD_NUMBER);
                        String showSentence = sentence.replaceAll(targetWordMatcher.group(SPLIT_NUMBER), targetWordMatcher.group(TARGET_WORD_NUMBER));

                        setGraphic(buildTextFlow(showSentence, targetWord));
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                        System.out.println(getTableRow().getHeight());
                    } else {
                        System.out.println(sentence);
                        setText("Something is wrong with result-handling!");
                        setTextFill(Color.BLACK);
                        setStyle("");
                        setContentDisplay(ContentDisplay.TEXT_ONLY);
                    }
                }
            }
        });
    }


    @FXML
    public void search(ActionEvent e) {

        String key = keyword.getText();
        String urlField = url.getText();

        if (Pattern.compile("[^0-9A-Za-z]").matcher(key).find()) {
            System.out.println("Invalid character!");
        }

        // if(urlField.contains(key)){
        // }

        ArrayList<SearchResult> results = new ArrayList<SearchResult>();

        try {
            results = Searcher.searchForTarget(key);
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }

        ObservableList<Sentence> sentences = table.getItems();

        if (sentences.size() > 0) {
            sentences.clear();
        }

        for (int i = 0; i < results.size(); i++) {
            sentences.add(new Sentence((i + 1), results.get(i)));
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

    private TextFlow buildTextFlow(final String sentence, final String targetWord) {
        TextFlow sentenceTextFlow = new TextFlow();
        int targetWordId = sentence.indexOf(targetWord);

        if (targetWordId > 0) {
            sentenceTextFlow.getChildren().add(new Text(sentence.substring(0, targetWordId)));
        }

        Text targetWordText = new Text(sentence.substring(targetWordId, targetWordId + targetWord.length()));
        targetWordText.setFill(Color.RED);
        targetWordText.setUnderline(true);

        sentenceTextFlow.getChildren().add(targetWordText);

        if (targetWordId + targetWord.length() < sentence.length()) {
            sentenceTextFlow.getChildren().add(new Text(sentence.substring(targetWordId + targetWord.length())));
        }

        return sentenceTextFlow;
    }
}
