package gui;

import searcher.datatype.SearchResult;
import javafx.beans.property.SimpleStringProperty;

public class Sentence {
/**
SimpleStringProperty to be used for TableView 
*/
    public SimpleStringProperty id = new SimpleStringProperty("");
    public SimpleStringProperty sentence = new SimpleStringProperty("");
    public SimpleStringProperty posTag = new SimpleStringProperty("");
    public SimpleStringProperty preTag = new SimpleStringProperty("");
    public SimpleStringProperty folTag = new SimpleStringProperty("");

    public Sentence(final String sentence, final String posTag, final String preTag, final String folTag) {

        this.sentence.set(sentence);
        this.posTag.set(posTag);
        this.preTag.set(preTag);
        this.folTag.set(folTag);
    }
/**
Constructor for sentence class for the TableView  
*/
    public Sentence(final int id, final SearchResult searchResult)
    {
        this.id.set(Integer.toString(id));
//        this.sentence.set(searchResult.getTargetInSentence());
        this.sentence.set(searchResult.getTargetInSentenceShort(2));
        this.posTag.set(searchResult.getTargetTag());
        this.preTag.set(searchResult.getPrecTag());
        this.folTag.set(searchResult.getFolTag());
    }
/**
Getters and setters
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
}
