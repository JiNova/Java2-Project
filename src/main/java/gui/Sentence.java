package gui;

/**
 * Created by Julia on 11.07.2017
 */

import backend.exceptions.ModuleNotInitializedException;
import javafx.beans.property.SimpleStringProperty;
import searcher.datatype.SearchResult;

/**
 * Sentence class as wrapper for all necessary informations, to make the display of the search results
 * in the ui rather handy
 */
public class Sentence {
    /**
     * SimpleStringProperty to be used for TableView
     */

    private int neighbours = 2;

    private SimpleStringProperty id = new SimpleStringProperty("");
    private SimpleStringProperty sentence = new SimpleStringProperty("");
    private SimpleStringProperty posTag = new SimpleStringProperty("");
    private SimpleStringProperty preTag = new SimpleStringProperty("None");
    private SimpleStringProperty folTag = new SimpleStringProperty("None");
    private SimpleStringProperty lemma = new SimpleStringProperty("");

    /**
     * Constructor for sentence class for the TableView
     */
    Sentence(final int id, final SearchResult searchResult, final int neighbours) {

        this.id.set(Integer.toString(id));
        try {
            this.sentence.set(searchResult.getTargetInSentenceShort(neighbours));
        }
        catch (ModuleNotInitializedException e) {
            this.sentence.set(searchResult.getTargetInSentence());
        }
        this.posTag.set(searchResult.getTargetTag());
        this.preTag.set(searchResult.getPrecTag());
        this.folTag.set(searchResult.getFolTag());
        this.neighbours = neighbours;
    }

    /**
     * Getters and setters
     */
    public String getId() {

        return id.get();
    }

    public void setId(String id) {

        this.id.set(id);
    }

    public String getSentence() {

        return sentence.get();
    }

    public void setSentence(String s) {

        sentence.set(s);
    }

    public String getPosTag() {

        return posTag.get();
    }

    public void setPosTag(String s) {

        this.posTag.set(s);
    }

    public String getPreTag() {

        return preTag.get();
    }

    public void setPreTag(String preTag) {

        this.preTag.set(preTag);
    }

    public String getFolTag() {

        return folTag.get();
    }

    public void setFolTag(String folTag) {

        this.folTag.set(folTag);
        ;
    }

    public int getNeighbours() {

        return neighbours;
    }

    public void setNeighbours(int neighbours) {

        this.neighbours = neighbours;
    }

    public String getLemma() {

        return lemma.get();
    }

    public void setLemma(String lemma) {

        this.lemma.set(lemma);
    }

    public SimpleStringProperty lemmaProperty() {

        return lemma;
    }
}
