package gui.util;

import gui.Sentence;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUIUtil {

    public static void showAlert(final Alert.AlertType type, final String title, final String message) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public static TextFlow buildTextFlow(final String sentence, final String targetWord) {

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

    public static boolean saveResults(final ObservableList<Sentence> sentences, final int targetWordGroup) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Results");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            PrintWriter output = null;

            try {
                output = new PrintWriter((new FileWriter(file)));
                int i = 1;
                for (Sentence sentence : sentences) {
                    Pattern targetSentencePattern = Pattern.compile("(.*?)(%([^%\\s]*)%)(.*?)");
                    Matcher targetWordMatcher = targetSentencePattern.matcher(sentence.getSentence());

                    if (targetWordMatcher.find()) {
                        String targetWord = targetWordMatcher.group(targetWordGroup);

                        output.write(i + ". Sentence: " + sentence.getSentence().replaceAll("%" + targetWord + "%", targetWord) + "\tLemma: " + sentence.getLemma() + "\tPreceeding Tag : "
                                + sentence.getPreTag() + "\tFollowing Tag: " + sentence.getFolTag());
                        output.println();
                        i++;
                    }
                }

                return true;
            }
            catch (IOException e) {
                throw e;
            }
            finally {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            }
        }
        else {
            return false;
        }
    }
}
