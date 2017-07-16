package backend;

/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private String text;
    private ArrayList<String[]> tkns;
    private ArrayList<String[]> POStags;

    public Parser(String s) {
        text = s;
        tkns = new ArrayList<String[]>();
        POStags = new ArrayList<String[]>();
    }

    /**
     * Method to get tags of tokens
     *
     * @param input tokens of the text
     * @return ArrayList of StringArrays that is filled with all tags
     * @throws FileNotFoundException
     */
    public static String[] getPosTag(String[] input) {
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("en-pos-maxent.bin");
            POSModel model = new POSModel(inputStream);
            POSTaggerME tagger = new POSTaggerME(model);


            return tagger.tag(input);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * Tokenizes a sentence into a single word
     *
     * @param input the sentence
     * @return ArrayList of StringArrays which contains all tokens of the sentences
     * @throws FileNotFoundException
     */
    public static String[] tokenizer(String input)
    {
        System.out.println("Tokenizing...");
        InputStream modelIn = null;
        try {
            modelIn = new FileInputStream("en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);
            return tokenizer.tokenize(input);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    /**
     * Returns a String of all words of the text in the same order as well as the tags
     *
     * @param tokensToPrint get all tokens of the text
     * @param tagsToPrint   get all tags of the tokens
     * @return a String to see the input text of the beginning with all its tags
     */
//    public String toString(ArrayList<String[]> tokensToPrint, ArrayList<String[]> tagsToPrint){
//        String result = "";
//
//        if(tokensToPrint != null && tagsToPrint!=null){
//            for(int i=0;i < sentences.size();i++){
//                for(int j=0;j<tokensToPrint.get(i).length;j++){
//                    result += tokensToPrint.get(i).clone()[j] + "_" + tagsToPrint.get(i).clone()[j] + " ";
//                }
//            }
//            return result;
//        }
//        return null;
//    }

    public static Map<String, String> getWordsTag(String[] words) {
        Map<String, String> results = new HashMap<String, String>();

        String[] tags = getPosTag(words);

        if (words.length != tags.length) {
            //We done goofed
            return null;
        } else {
            for (int i = 0; i < words.length; i++) {
                results.put(words[i], tags[i]);
            }

            return results;
        }
    }

    /**
     * Method to split all sentences in the text to a ArrayList
     *
     * @return ArrayList with all sentences of the text
     * @throws IOException
     */
    public static String[] sentenceSplitter(final String text) throws IOException
    {
        System.out.println("Splitting sentences...");
        InputStream modelIn = new FileInputStream("en-sent.bin");
        try {

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            String[] sentences = sentenceDetector.sentDetect(text);

            return sentences;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (final IOException e) {

                }
            }
        }
        return null;
    }
}
