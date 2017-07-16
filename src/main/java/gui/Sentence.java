package gui;

import javafx.beans.property.SimpleStringProperty;

public class Sentence {

 public SimpleStringProperty sentence = new SimpleStringProperty("");
 public SimpleStringProperty preTag = new SimpleStringProperty("");
 public SimpleStringProperty fTag = new SimpleStringProperty("");
 public Sentence(String sentence, String preTag, String fTag) {
   setfTag(fTag);
   setPreTag(preTag);
   setSentence(sentence);
 }
 public String getSentence() {
  return sentence.get();
 }
 public void setSentence(String s) {
  sentence.set(s);
 }
 public String getPreTag() {
  return preTag.get();
 }
 public void setPreTag(String preTag) {
  this.preTag.set(preTag);
 }
 public String getfTag() {
  return fTag.get();
 }
 public void setfTag(String fTag) {
  this.fTag.set(fTag);;
 }
}
