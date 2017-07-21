package searcher;

/**
 * Created by Daniela on 12.07.2017.
 */

import backend.TextProvider;
import backend.TextProviderFactory;
import backend.exceptions.ModuleNotInitializedException;
import main.Main;
import org.apache.commons.lang3.StringUtils;
import searcher.datatype.SearchResult;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Searcher {

    /**
     * Search for a provided word in a file or webpage
     *
     * @param targetWord   The word to search for
     * @param path         The path/url to the file/webpage
     * @param providerType The TextProvider type, depending on the source being a local file or url
     * @return ArrayList with all the search results
     * @throws IOException
     * @throws ModuleNotInitializedException If the Parser-models have not been loaded at application start. This shoudl not happen, as
     *                                       the Application is forced to close itself, if it fails to load even one of the models
     */
    public static ArrayList<SearchResult> searchForTargetWord(final String targetWord, final String path, final TextProviderFactory.PROVIDER_TYPES providerType) throws IOException, ModuleNotInitializedException {

        BufferedReader br = null;

        try {

            //Get the right TextProvider
            TextProvider provider = TextProviderFactory.getTextProvider(path, providerType);

            if (provider != null) {
                br = new BufferedReader(provider.getContentReader());
            }
            else {
                throw new ModuleNotInitializedException("Provider was not set-up correctly!");
            }

            String[] wordsOnLine;
            String line;
            ArrayList<SearchResult> foundTargets = new ArrayList<SearchResult>();

            while ((line = br.readLine()) != null) {

                String[] sentences;

                //Check if target-word is in the line, if yes, split the sentences
                if (StringUtils.containsIgnoreCase(line, targetWord)) {
                    sentences = Main.getParser().splitSentences(line);
                }
                else {
                    continue;
                }

                //Now let's search for the sentences, which actually contain the target-word
                for (int sentenceId = 0; sentenceId < sentences.length; sentenceId++) {


                    if (StringUtils.containsIgnoreCase(sentences[sentenceId], targetWord)) {
                        wordsOnLine = Main.getParser().tokenize(sentences[sentenceId]); //split the line into separate words

                        for (int i = 0; i < wordsOnLine.length; i++) {
                            if (wordsOnLine[i].equalsIgnoreCase(targetWord)) {

                                foundTargets.add(saveResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine));
                            }
                        }
                    }
                }
            }

            return foundTargets;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find file");
        }
        catch (IOException e) {
            throw new IOException("Error while accessing file");
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    throw new IOException("Could not close BufferedReader");
                }
            }
        }
    }

    /**
     * Search for a provided lemma in a webpage or local file
     *
     * @param targetLemma  The lemma to search for
     * @param path         The file-path/url to the source
     * @param providerType The TextProvider type, depending on the source being a local file or url
     * @return
     * @throws IOException
     * @throws ModuleNotInitializedException If the Parser-models have not been loaded at application start. This shoudl not happen, as
     *                                       the Application is forced to close itself, if it fails to load even one of the models
     */
    public static ArrayList<SearchResult> searchForTargetLemma(final String targetLemma, final String path, final TextProviderFactory.PROVIDER_TYPES providerType) throws IOException, ModuleNotInitializedException {

        System.out.println("Searching for lemma: " + targetLemma);

        BufferedReader br = null;

        try {

            TextProvider provider = TextProviderFactory.getTextProvider(path, providerType);

            if (provider != null) {
                br = new BufferedReader(provider.getContentReader());
            }
            else {
                throw new ModuleNotInitializedException("Provider was not set-up correctly!");
            }

            String[] wordsOnLine;
            String line;
            ArrayList<SearchResult> foundTargets = new ArrayList<SearchResult>();

            while ((line = br.readLine()) != null) {

                String[] sentences = Main.getParser().splitSentences(line);

                for (int sentenceId = 0; sentenceId < sentences.length; sentenceId++) {

                    wordsOnLine = Main.getParser().tokenize(sentences[sentenceId]); //split the line into separate words
                    String[] tagResult = Main.getParser().getPosTag(wordsOnLine);
                    String[] lemmaResult = Main.getParser().getLemma(wordsOnLine, tagResult);

                    for (int i = 0; i < lemmaResult.length; i++) {

                        if (targetLemma.equalsIgnoreCase(lemmaResult[i])) {
                            foundTargets.add(saveResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine));
                        }
                    }
                }
            }

            return foundTargets;
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("Could not find file");
        }
        catch (IOException e) {
            throw new IOException("Error while accessing file", e);
        }
        catch (ModuleNotInitializedException e) {
            throw e;
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }
                catch (IOException e) {
                    throw new IOException("Could not close BufferedReader");
                }
            }
        }
    }

    /**
     * Save the found result to a SearchResult object
     *
     * @param wordIndex     id of the target-word in the provided sentence-parts-array
     * @param word          The target-word as String
     * @param sentence      The full sentence as a string
     * @param sentenceParts The sentence split up in all the tokens as an String-array
     * @return The SearchResult-object
     * @throws ModuleNotInitializedException If the Parser-models have not been loaded at application start. This shoudl not happen, as
     *                                       the Application is forced to close itself, if it fails to load even one of the models
     */
    private static SearchResult saveResult(final int wordIndex, final String word, final String sentence, final String[] sentenceParts) throws ModuleNotInitializedException {

        SearchResult result = new SearchResult(wordIndex, word, sentence, sentenceParts);

        String precWord = "";
        String folWord = "";

        if (wordIndex != 0) { //wenn wort nicht am satzanfang ist
            precWord = sentenceParts[wordIndex - 1];
        }

        if (wordIndex + 1 != sentenceParts.length) { //wenn i+1 nicht das ende von dem array ist
            folWord = sentenceParts[wordIndex + 1];
        }

        String words[] = {precWord, word, folWord};
        Map<String, String> wordsAndTags = Main.getParser().getWordsTag(words);

        String lemma = Main.getParser().getLemma(new String[]{word}, new String[]{wordsAndTags.get(word)})[0];

        result.setTargetTag(wordsAndTags.get(word));
        result.setPrecTag(wordsAndTags.get(precWord));
        result.setFolTag(wordsAndTags.get(folWord));
        result.setLemma(lemma);

        return result;
    }
}
