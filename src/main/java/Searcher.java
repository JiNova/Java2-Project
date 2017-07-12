/**
 * Created by Daniela on 12.07.2017.
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.*;
import java.util.*;


public class Searcher {

    /**
     * Created by Ela on 11.07.2017.
     * get word from user
     * search for word occurrences in text
     * save sentences in which word occurs
     * search-word will be put in all capital letters
     * take each sentence and get (classify) POS-tag for each case of the word -> print
     * count how often word occurs as particular tags -> print
     * check all sentences and get all preceding tags, list them and count -> print
     * check all sentences and get all following tags, list them and count -> print
     * search for Lemma or Actual Form (whatever user chooses) (or maybe we just do for actual form first)
     */

        /*
         no need for file, get arraylist sentences, arraylist with tokens and arraylist with tags ins same reihenfolge as tokens
        */
    String targetWord;

    /**
     * Constructor (?) that sets targetWord to empty String
     *
     * @param word the word that is being looked for
     */
    public Searcher(targetWord) {
        targetWord = "";
    }

    /**
     * search the array for the target word, and get the sentences in which the word occurs
     * return null if no sentence was found with the word
     * return sentences in which word occurs
     */
    public void searchForTarget() {
        ArrayList<String> sentenceList = ??.getSentences();
        //use arrayList sentence
        //search if the sentences contain target word
        //if yes, return the sentence it appears in and put the target word in all capital letters
        //if not, return message with info

        //if XY contains targetWord DO SOMETHING
    }

    /**
     * count occurences of the targetWord in whole text
     */
    public void countOccurenceOfTarget() {
        //count how often target word appeared in total
            while (!found) {
                //go through arraylist
                //if find word, found = true
                //count how often word is found
                //if can't find word
            }
    }

    /**
     * get the tag for the targetWord
     */
    public void getTargetTag() {
        //get the tag for the target word for each sentence it appears in
        //save them somewhere so we can show them to the user later
        //maybe put in a list or something?
    }

    /**
     * get occurences of the tags the target appears in
     * eg:  NN  20      VP  10
     */
    public void getTagOccurenceTarget() {
        //for each tag for the target word count how often it appeared
        //use hashmap?
    }

    /**
     * get all the preceding tags of the occurence of the word
     */
    public void getPrecedingTag() {
        //for each occurence of the word get the tag of the preceding word
        //save in another hasmap?

    }

    /**
     * get all the following tags of the occurence of the word
     */
    public void getFollowingTag() {
        //for each occurence of the word get the tag of the following word
        //save in another hasmap?

    }
}
}
