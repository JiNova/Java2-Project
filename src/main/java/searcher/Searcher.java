package searcher;

/**
 * Created by Daniela on 12.07.2017.
 */

import backend.Parser;
import backend.TextProvider;
import backend.TextProviderFactory;
import searcher.datatype.SearchResult;
import org.apache.commons.lang3.StringUtils;

import java.io.FileNotFoundException;
import java.io.*;
import java.util.*;


public class Searcher {

    public static ArrayList<SearchResult> searchForTargetWord(final String targetWord, final String path, final TextProviderFactory.PROVIDER_TYPES providerType) throws IOException {

        System.out.println("Searching for word: " +targetWord);

        BufferedReader br = null;

        try {

            TextProvider provider = TextProviderFactory.getTextProvider(path, providerType);

            br = new BufferedReader(provider.getContentReader());

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

                                foundTargets.add(saveResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine));
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

    public static ArrayList<SearchResult> searchForTargetLemma(final String targetLemma, final String path, final TextProviderFactory.PROVIDER_TYPES providerType) throws IOException {
        System.out.println("Searching for lemma: " + targetLemma);

        BufferedReader br = null;

        try {

            TextProvider provider = TextProviderFactory.getTextProvider(path, providerType);

            br = new BufferedReader(provider.getContentReader());

            String[] wordsOnLine;
            String line;
            ArrayList<SearchResult> foundTargets = new ArrayList<SearchResult>();

            while ((line = br.readLine()) != null) {

                String[] sentences = Parser.sentenceSplitter(line);

                for (int sentenceId = 0; sentenceId < sentences.length; sentenceId++) {

                    wordsOnLine = Parser.tokenizer(sentences[sentenceId]); //split the line into separate words
                    String[] tagResult = Parser.getPosTag(wordsOnLine);
                    String[] lemmaResult = Parser.getLemma(wordsOnLine, tagResult);

                        for (int i = 0; i < lemmaResult.length; i++) {

                            if (targetLemma.equalsIgnoreCase(lemmaResult[i]))
                            {
                                foundTargets.add(saveResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine));
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

    private static SearchResult saveResult(final int wordIndex, final String word, final String sentence, final String[] sentenceParts)
    {
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
        Map<String, String> wordsAndTags = Parser.getWordsTag(words);

        result.setTargetTag(wordsAndTags.get(word));
        result.setPrecTag(wordsAndTags.get(precWord));
        result.setFolTag(wordsAndTags.get(folWord));

        return result;
    }
}
