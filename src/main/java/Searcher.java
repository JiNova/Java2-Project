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
    public void searchForTarget() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("text.txt"));
        String[] wordsOnLine;
        String line;
        String targetWord = "";
        ArrayList<SearchResult> foundTarget = new ArrayList<SearchResult>();

        while ((line = br.readLine()) != null) {
            String sentence = line;
            wordsOnLine = line.split("\\s"); //split the line into separate words

            for (int i = 0; i < wordsOnLine.length; i++) {
                if (wordsOnLine[i].equalsIgnoreCase(targetWord)) {
                    SearchResult result = new SearchResult(targetWord, sentence);

                    String precWord = "";
                    String folWord = "";

                    if (i != 0) { //wenn wort nicht am satzanfang ist
                        precWord = wordsOnLine[i - 1];
                    }

                    if (i + 1 != wordsOnLine.length) { //wenn i+1 nicht das ende von dem array ist
                        folWord = wordsOnLine[i + 1];
                    }

                    String words[] = {precWord, targetWord, folWord};
                    Map<String, String> wordsAndTags = Parser.getWordsTag(words);

                    result.setTargetTag(wordsAndTags.get(targetWord));
                    result.setPrecTag(wordsAndTags.get(precWord));
                    result.setFolTag(wordsAndTags.get(folWord));

                    foundTarget.add(result);
                }
            }
        }
    }

    //mit in die methode oben packen

    /**
     * Count occurences of the targetWord in a text
     * @param targetWord the word to count
     * @param file the text to search the word in
     * @return return the count of the word
     */
    public int countOccurenceOfTarget(String targetWord, File file) throws FileNotFoundException {
        //count how often target word appeared in total
        {
            int count = 0;
            targetWord = targetWord.trim();
            //BufferedReader benutzen
            Scanner scanner = new Scanner(new FileReader(file));

            while (scanner.hasNext())
            {
                String nextWord = scanner.next().trim();
                if (nextWord.equals(targetWord)) {
                    ++count;
                }
            }
            return count;
        }
    }


    /**
     * get the tag for the targetWord
     */
    public void getTags(String[] tags) {

        //use getWordsTag from Parser
        //targetWord is the key, tag values
        word -> tag;
        prec -> tag;
        fol -> tag;

        return tag vom word
        //get the tag for the target word for each sentence it appears in
        //save them somewhere so we can show them to the user later
        //maybe put in a list or something?
    }

    /**
     * get occurences of the tags the target appears in
     * eg:  NN  20      VP  10
     */

    /**
     * get all the preceding tags of the occurence of the word
     */
    public void getPrecedingTag() {

        //selbe wie bei get Target tag,nur anderer key
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
