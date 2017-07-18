package backend;

/**
 * Created by Daniela on 12.07.2017.
 */

import backend.datatype.SearchResult;
import org.apache.commons.lang3.StringUtils;

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

    /**
     * search the array for the target word, and get the sentences in which the word occurs
     * return null if no sentence was found with the word
     * return sentences in which word occurs
     */
    public static ArrayList<SearchResult> searchForTarget(final String targetWord) throws IOException {

        System.out.println("Searching for " +targetWord);

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("text.txt"));

            String[] wordsOnLine;
            String line;
            ArrayList<SearchResult> foundTargets = new ArrayList<SearchResult>();

            while ((line = br.readLine()) != null) {

                String[] sentences = null;

                if (StringUtils.containsIgnoreCase(line, targetWord))
                {
                    sentences = Parser.sentenceSplitter(line);
                }
                else
                {
                    continue;
                }

                for (int sentenceId = 0; sentenceId < sentences.length; sentenceId++) {

                    if (StringUtils.containsIgnoreCase(sentences[sentenceId], targetWord)) {
                        wordsOnLine = Parser.tokenizer(sentences[sentenceId]); //split the line into separate words

                        for (int i = 0; i < wordsOnLine.length; i++) {
                            if (wordsOnLine[i].equalsIgnoreCase(targetWord)) {
                                SearchResult result = new SearchResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine);

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

                                foundTargets.add(result);
                            }
                        }
                    }
                }
            }

            return foundTargets;
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Could not find file");
        }
        catch (IOException e)
        {
            throw new IOException("Error while accessing file");
        }
        finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    throw new IOException("Could not close BufferedReader");
                }
            }
        }
    }
}
