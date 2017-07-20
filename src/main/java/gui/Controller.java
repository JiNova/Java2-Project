package gui;

import backend.TextProviderFactory;
import backend.exceptions.ModuleNotInitializedException;
import gui.util.GUIUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import searcher.Searcher;
import searcher.datatype.SearchResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controls the FXML File , methods for buttons and text fields
 */
public class Controller {

    private static final int SPLIT_NUMBER = 2;
    private static final int TARGET_WORD_NUMBER = 3;
    private static final int NEIGHBOUR_DEFAULT = 2;
    private final ObservableList<Integer> neighbourOpt = FXCollections.observableArrayList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    );

    private boolean fetchFromFile = true;
    private boolean searchForWord = true;

    private ToggleGroup searchMethod = new ToggleGroup();

    @FXML
    private TextField url;
    @FXML
    private TextField keyword;
    @FXML
    private ToggleButton wordButton;
    @FXML
    private ToggleButton lemmaButton;
    @FXML
    private Button searchButton;
    @FXML
    private ComboBox neighbours;
    @FXML
    private TableView<Sentence> table;
    @FXML
    private TableColumn<Sentence, String> sentenceColumn;
    @FXML
    private AnchorPane miscButtons;

    @FXML
    private void initialize() {

        url.textProperty().addListener((observableValue, oldUrl, newUrl) -> {

            if (newUrl.isEmpty()) {
                searchButton.setDisable(true);
            } else {
                searchButton.setDisable(false);
            }

            if (fetchFromFile && newUrl.startsWith("http")) {
                fetchFromFile = false;
            }
        });

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

        searchMethod.selectedToggleProperty().addListener(
                (observable, oldToggle, newToggle) -> {
                    if (null != newToggle) {
                        if ("search_word".equals(newToggle.getUserData()))
                        {
                            this.searchForWord = true;
                            System.out.println("Word search selected");
                        }
                        else
                        {
                            this.searchForWord = false;
                            System.out.println("Lemma search selected");
                        }
                    }
                });

        this.wordButton.setToggleGroup(this.searchMethod);
        this.lemmaButton.setToggleGroup(this.searchMethod);


        this.neighbours.getItems().addAll(neighbourOpt);
        this.neighbours.getSelectionModel().select(NEIGHBOUR_DEFAULT - 1);
    }

    @FXML
    public void open(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            this.url.setText(selectedFile.getAbsolutePath());
            this.fetchFromFile = true;
        } else {
            System.out.println("File selection cancelled.");
        }
    }

    @FXML
    public void search(ActionEvent e) {

        final long startTime = System.nanoTime();
        final String key = keyword.getText();
        final String urlField = url.getText();

        if (key == null || key.isEmpty()) {
            GUIUtil.showAlert(Alert.AlertType.ERROR, "No keyword",
                    "Please specify a keyword to search for!");

            return;
        }

        if (Pattern.compile("(.*)[^0-9A-Za-zßÄÖÜäöü](.*)").matcher(key).find()) {

            GUIUtil.showAlert(Alert.AlertType.ERROR, "Invalid character",
                    "You may only search for words consisting of letters and digits!");

            return;
        }

        ArrayList<SearchResult> results;

        try
        {
            if (this.searchForWord)
            {
                results = Searcher.searchForTargetWord(key, urlField, (this.fetchFromFile ? TextProviderFactory.PROVIDER_TYPES.FILE : TextProviderFactory.PROVIDER_TYPES.WEB));
            }
            else
            {
                results = Searcher.searchForTargetLemma(key, urlField, (this.fetchFromFile ? TextProviderFactory.PROVIDER_TYPES.FILE : TextProviderFactory.PROVIDER_TYPES.WEB));
            }
        }
        catch (IOException e1) {

            GUIUtil.showAlert(Alert.AlertType.ERROR, "Error", e1.getMessage());
            return;
        }
        catch(ModuleNotInitializedException e2)
        {
            GUIUtil.showAlert(Alert.AlertType.ERROR, "Error", e2.getMessage());
            return;
        }

        if (results.size() == 0) {
            System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " seconds");
            GUIUtil.showAlert(Alert.AlertType.INFORMATION, "No Result",
                    "Unfortunately, we were unable to find the provided keyword. Sad :(");

            return;
        }

        ObservableList<Sentence> sentences = table.getItems();

        if (sentences.size() > 0) {
            sentences.clear();
        }

        for (int i = 0; i < results.size(); i++) {
            sentences.add(new Sentence((i + 1), results.get(i), (Integer) neighbours.getSelectionModel().getSelectedItem()));
        }

        miscButtons.setVisible(true);
        System.out.println((System.nanoTime() - startTime) / 1000000000.0 + " seconds");
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

    /**
     * Clean all fields
     */

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
