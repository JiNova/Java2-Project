package searcher.datatype;

import backend.exceptions.ModuleNotInitializedException;
import main.Main;

import java.util.Arrays;

/**
 * Created by Andreas on 14.07.17.
 */

/**
 * Saves and represents a search result with a variety of informations
 */
public class SearchResult {
    private int wordId;
    private String targetWord;
    private String targetSentence;
    private String[] sentenceParts;
    private String targetTag;
    private String precTag;
    private String folTag;
    private String lemma;

    public SearchResult(final int wordId, final String targetWord, final String targetSentence, final String[] sentenceParts) {

        this.wordId = wordId;
        this.targetWord = targetWord;
        this.sentenceParts = sentenceParts;
        this.targetSentence = targetSentence.replaceAll(targetWord, "%w");
    }


    public String getTargetTag() {

        return targetTag;
    }

    public void setTargetTag(String targetTag) {

        this.targetTag = targetTag;
    }

    public String getPrecTag() {

        return precTag;
    }

    public void setPrecTag(String precTag) {

        this.precTag = precTag;
    }

    public String getFolTag() {

        return folTag;
    }

    public void setFolTag(String folTag) {

        this.folTag = folTag;
    }

    public String getTargetWord() {

        return targetWord;
    }

    public void setTargetWord(String targetWord) {

        this.targetWord = targetWord;
    }

    public String getTargetSentence() {

        return targetSentence;
    }

    public void setTargetSentence(String targetSentence) {

        this.targetSentence = targetSentence;
    }

    public String getTargetInSentence() {

        return this.getTargetSentence().replaceAll("%w", this.targetWord);
    }

    /**
     * Get a shortened version of the sentence, which only shows a narrowed down version of the sentence with a
     * specific amount of neighbours. If the target-word has less preceding or following neighbours, than specified,
     * the method will adjust the amount of neighbours to display in the respective direction.
     *
     * @param neighbours The neighbours to the target-word to list.
     * @return The sentence in a shortened version a la "[..] neighbour neighbour target-word neighbour neighbour"
     */
    public String getTargetInSentenceShort(final int neighbours) throws ModuleNotInitializedException {

        //We use these two to determine the maximum amount of neighbours
        //we will be able to list
        int maxPreNeighborId = 0;
        int maxFolNeighborId = this.wordId + neighbours;

        if (this.wordId > 0) {
            //Number of preceding neighbours the user would like to display
            maxPreNeighborId = this.wordId - neighbours;

            //Find the actual number of preceding neighbours we can display
            while (maxPreNeighborId < 0) {
                ++maxPreNeighborId;
            }
        }

        //Find the actual number of following neighbours we can display
        while (maxFolNeighborId > (this.sentenceParts.length - 1)) {
            --maxFolNeighborId;
        }

        String[] shortSentenceParts = Arrays.copyOfRange(sentenceParts, maxPreNeighborId, maxFolNeighborId + 1);
        String[] shortSentenceTags = Main.getParser().getPosTag(shortSentenceParts);

        StringBuilder sentence = new StringBuilder((maxPreNeighborId > 0 ? "[..] " : ""));

        for (int i = 0; i < shortSentenceParts.length; i++) {
            if (shortSentenceParts[i].equalsIgnoreCase(targetWord)) {
                sentence.append("%").append(shortSentenceParts[i]).append("%");
            }
            else {
                sentence.append(shortSentenceParts[i]);
            }

            if (i < (shortSentenceParts.length - 1)) {
                if (!shortSentenceTags[i + 1].matches("[.,:]|(POS)")) {
                    sentence.append(" ");
                }
            }
        }

        sentence.append((maxFolNeighborId < this.sentenceParts.length - 1 ? " [..]" : ""));

        return sentence.toString();
    }

    public String getLemma() {

        return lemma;
    }

    public void setLemma(String lemma) {

        this.lemma = lemma;
    }
}
