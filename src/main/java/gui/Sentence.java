package gui;

import backend.datatype.SearchResult;
import javafx.beans.property.SimpleStringProperty;

public class Sentence {

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

    public Sentence(final int id, final SearchResult searchResult)
    {
        this.id.set(Integer.toString(id));
        this.sentence.set(searchResult.getTargetInSentence());
        this.posTag.set(searchResult.getTargetTag());
        this.preTag.set(searchResult.getPrecTag());
        this.folTag.set(searchResult.getFolTag());
    }

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
