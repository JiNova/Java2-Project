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

//    public static ArrayList<SearchResult> searchForTargetLemma(final String targetLemma, final String path, final TextProviderFactory.PROVIDER_TYPES providerType)
//    {
//        System.out.println("Searching for lemma: " + targetLemma);
//
//        BufferedReader br = null;
//
//        try {
//
//            TextProvider provider = TextProviderFactory.getTextProvider(path, providerType);
//
//            br = new BufferedReader(provider.getContentReader());
//
//            String[] wordsOnLine;
//            String line;
//            ArrayList<SearchResult> foundTargets = new ArrayList<SearchResult>();
//
//            while ((line = br.readLine()) != null) {
//
//                String[] sentences = null;
//
//                if (StringUtils.containsIgnoreCase(line, targetLemma))
//                {
//                    sentences = Parser.sentenceSplitter(line);
//                }
//                else
//                {
//                    continue;
//                }
//
//                for (int sentenceId = 0; sentenceId < sentences.length; sentenceId++) {
//
//                    if (StringUtils.containsIgnoreCase(sentences[sentenceId], targetWord)) {
//                        wordsOnLine = Parser.tokenizer(sentences[sentenceId]); //split the line into separate words
//
//                        for (int i = 0; i < wordsOnLine.length; i++) {
//                            if (wordsOnLine[i].equalsIgnoreCase(targetWord)) {
//                                SearchResult result = new SearchResult(i, wordsOnLine[i], sentences[sentenceId], wordsOnLine);
//
//                                String precWord = "";
//                                String folWord = "";
//
//                                if (i != 0) { //wenn wort nicht am satzanfang ist
//                                    precWord = wordsOnLine[i - 1];
//                                }
//
//                                if (i + 1 != wordsOnLine.length) { //wenn i+1 nicht das ende von dem array ist
//                                    folWord = wordsOnLine[i + 1];
//                                }
//
//                                String words[] = {precWord, targetWord, folWord};
//                                Map<String, String> wordsAndTags = Parser.getWordsTag(words);
//
//                                result.setTargetTag(wordsAndTags.get(targetWord));
//                                result.setPrecTag(wordsAndTags.get(precWord));
//                                result.setFolTag(wordsAndTags.get(folWord));
//
//                                System.out.println(Parser.getLemma(new String[]{targetWord}, new String[]{wordsAndTags.get(targetWord)})[0]);
//
//                                foundTargets.add(result);
//                            }
//                        }
//                    }
//                }
//            }
//
//            return foundTargets;
//        }
//        catch (FileNotFoundException e)
//        {
//            throw new FileNotFoundException("Could not find file");
//        }
//        catch (IOException e)
//        {
//            throw new IOException("Error while accessing file");
//        }
//        finally
//        {
//            if (br != null)
//            {
//                try
//                {
//                    br.close();
//                }
//                catch (IOException e)
//                {
//                    throw new IOException("Could not close BufferedReader");
//                }
//            }
//        }
//    }
}
