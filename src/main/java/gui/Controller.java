package gui;

/**
 * Created by Julia on 11.07.2017
 * Modified by Andy
 */

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
import javafx.stage.FileChooser;
import main.Main;
import searcher.Searcher;
import searcher.datatype.SearchResult;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
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

    private static final String FILE_MODE_DESC = "File";
    private static final String HTTP_MODE_DESC = "http";
    private static final String KEY_WORD_PATTERN = "(.*)[^0-9A-Za-zßÄÖÜäöü](.*)"; //The allowed pattern for a keyword/lemma

    private final ObservableList<Integer> neighbourOpt = FXCollections.observableArrayList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    );
    private boolean fetchFromFile = true;
    private boolean searchForWord = true;

    private ToggleGroup searchMethod = new ToggleGroup();

    @FXML
    private Label searchLabel;
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
    private Text sourceMode;
    @FXML
    private Label searchTimeLabel;
    @FXML
    private Text searchTime;

    /**
     * Initialize the Controller, setup all necessary listeners and
     * perform some layout-settings
     */
    @FXML
    private void initialize() {

        url.textProperty().addListener((observableValue, oldUrl, newUrl) -> {

            if (newUrl.isEmpty()) {
                searchButton.setDisable(true);
            }
            else {
                searchButton.setDisable(false);
            }

            if (fetchFromFile && newUrl.startsWith("http")) {
                fetchFromFile = false;
                sourceMode.setText(HTTP_MODE_DESC);
            }
            else if (!fetchFromFile && !newUrl.startsWith("http")) {
                fetchFromFile = true;
                sourceMode.setText(FILE_MODE_DESC);
            }
        });

        sentenceColumn.setCellFactory(sentenceColumn -> new TableCell<Sentence, String>() {

            @Override
            protected void updateItem(final String sentence, final boolean empty) {

                if (sentence == null || empty) {
                    setGraphic(null);
                    setText(null);
                    setStyle("");
                }
                else {
                    setGraphic(null);

                    Pattern targetSentencePattern = Pattern.compile("(.*?)(%([^%\\s]*)%)(.*?)");
                    Matcher targetWordMatcher = targetSentencePattern.matcher(sentence);

                    if (targetWordMatcher.find()) {
                        String targetWord = targetWordMatcher.group(TARGET_WORD_NUMBER);
                        String showSentence = sentence.replaceAll(targetWordMatcher.group(SPLIT_NUMBER), targetWordMatcher.group(TARGET_WORD_NUMBER));

                        setGraphic(GUIUtil.buildTextFlow(showSentence, targetWord));
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                    else {
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
                        if ("search_word".equals(newToggle.getUserData())) {
                            this.searchForWord = true;
                            searchLabel.setText("Keyword:");
                        }
                        else {
                            this.searchForWord = false;
                            searchLabel.setText("Lemma:");
                        }
                    }
                });

        this.wordButton.setToggleGroup(this.searchMethod);
        this.lemmaButton.setToggleGroup(this.searchMethod);

        this.neighbours.getItems().addAll(neighbourOpt);
        this.neighbours.getSelectionModel().select(NEIGHBOUR_DEFAULT - 1);

        this.sourceMode.setText("File");
    }

    /**
     * Open a file on the user's harddrive to scan
     *
     * @param e
     */
    @FXML
    public void open(ActionEvent e) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filterText = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter filterAll = new FileChooser.ExtensionFilter("All files", "*");
        fileChooser.getExtensionFilters().add(filterText);
        fileChooser.getExtensionFilters().add(filterAll);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            this.url.setText(selectedFile.getAbsolutePath());
            this.fetchFromFile = true;
            this.sourceMode.setText(FILE_MODE_DESC);
        }
    }

    /**
     * Search the provided file or website for a provided keyword or lemma
     *
     * @param e
     */
    @FXML
    public void search(ActionEvent e) {

        final long startTime = System.nanoTime();
        final String key = keyword.getText();
        final String urlField = url.getText();

        //Abort if keyword or filepath/url are not set
        if (key == null || key.isEmpty()) {

            return;
        }

        if (urlField == null || urlField.isEmpty()) {
            return;
        }

        //Check if the user is searching for valid words
        if (Pattern.compile(KEY_WORD_PATTERN).matcher(key).find()) {

            GUIUtil.showAlert(Alert.AlertType.ERROR, "Invalid character",
                    "You may only search for words consisting of letters and digits!");

            return;
        }

        ArrayList<SearchResult> results;

        try {
            if (this.searchForWord) {
                results = Searcher.searchForTargetWord(key, urlField, (this.fetchFromFile ? TextProviderFactory.PROVIDER_TYPES.FILE : TextProviderFactory.PROVIDER_TYPES.WEB));
            }
            else {
                results = Searcher.searchForTargetLemma(key, urlField, (this.fetchFromFile ? TextProviderFactory.PROVIDER_TYPES.FILE : TextProviderFactory.PROVIDER_TYPES.WEB));
            }
        }
        catch (IOException | ModuleNotInitializedException e1) {

            GUIUtil.showAlert(Alert.AlertType.ERROR, "Error", e1.getMessage());
            return;
        }

        if (results.size() == 0) {
            GUIUtil.showAlert(Alert.AlertType.INFORMATION, "No Result",
                    "Unfortunately, we were unable to find the provided " + (searchForWord ? "keyword" : "lemma") +". Sad :(");

            return;
        }

        ObservableList<Sentence> sentences = table.getItems();

        //Clear table, if there are old values present
        if (sentences.size() > 0) {
            sentences.clear();
        }

        //Fill table with all necessary informations
        for (int i = 0; i < results.size(); i++) {
            Sentence sentence = new Sentence((i + 1), results.get(i), (Integer) neighbours.getSelectionModel().getSelectedItem());
            sentence.setLemma(results.get(i).getLemma());
            sentences.add(sentence);
        }

        //Make some Clear and Save buttons visible, display search-request time
        miscButtons.setVisible(true);
        String time = new DecimalFormat("#.#### seconds").format((System.nanoTime() - startTime) / 1000000000.0);
        searchTimeLabel.setVisible(true);
        searchTime.setText(time);
        searchTime.setVisible(true);
    }

    /**
     * Save the current results to a txt-file
     *
     * @param e
     */
    @FXML
    public void save(ActionEvent e) {

        //Abort, if there are no results to save
        if (table.getItems().size() == 0) {
            return;
        }

        try {
            if (GUIUtil.saveResults(table.getItems(), TARGET_WORD_NUMBER)) {
                GUIUtil.showAlert(Alert.AlertType.INFORMATION, "Saved", "File saved successfully!");
            }
        }
        catch (IOException e1) {
            GUIUtil.showAlert(Alert.AlertType.ERROR, "Error", e1.getMessage());
        }

    }

    /**
     * Clean all fields and hide some ui-elements
     */
    @FXML
    public void clean(ActionEvent e) {

        System.out.println("Cleaning UI...");
        table.getItems().clear();
        url.clear();
        keyword.clear();
        miscButtons.setVisible(false);
        searchTimeLabel.setVisible(false);
        searchTime.setText("");
        searchTime.setVisible(false);
    }
}
