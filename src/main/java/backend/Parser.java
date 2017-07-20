package backend;

/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.*;
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
        System.out.println("Tagging...");
        //initialize inputstream
        InputStream inputStream = null;

            try {
                //load the model file
                inputStream = new FileInputStream("en-pos-maxent.bin");
                POSModel model = new POSModel(inputStream);
                POSTaggerME tagger = new POSTaggerME(model);

                //returns a StringArray, which contains all tags
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
        //initialize InputStream
        InputStream modelIn = null;
        try {
            //load the model file
            modelIn = new FileInputStream("en-token.bin");
            TokenizerModel model = new TokenizerModel(modelIn);
            Tokenizer tokenizer = new TokenizerME(model);
            //tokenizes the text String and returns a StringArray with all tokens
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
     * save tokens and POS tags into a Map
     * @param words
     * @return
     */
    public static Map<String, String> getWordsTag(String[] words) {
        Map<String, String> results = new HashMap<String, String>();

        //get the POS tags of all words
        String[] tags = getPosTag(words);
        //same amount of tags and tokens
        if (words.length != tags.length) {
            //We done goofed
            return null;
        } else {
            //iterate through all tokens and tags
            for (int i = 0; i < words.length; i++) {
                //save current token as key and tag as value
                results.put(words[i], tags[i]);
            }
            //return the map
            return results;
        }
    }

    /**
     * Lemmas of the text
     * @param words get all tokens of the text
     * @param tags get all tags of the text
     * @return StringArray that cointains all lemmas
     */
    public static String[] getLemma(String[] words, String[] tags){
        InputStream modelIn=null;
        String[] lemmas = null;
        try{
            modelIn = new FileInputStream("en-lemmatizer.bin");
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(modelIn);
            if(words.length != tags.length){
                return null;
            }else{
                lemmas = lemmatizer.lemmatize(words,tags);
                return lemmas;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method to split all sentences in the text to a ArrayList
     *
     * @return ArrayList with all sentences of the text
     * @throws IOException
     */
    public static String[] sentenceSplitter(final String text) throws IOException
    {
        //initialize InputStream and load model file
        InputStream modelIn = new FileInputStream("en-sent.bin");
        try {
            //initialize the SentenceModel and the SentenceDetector
            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            //Split the String text into a StringArray, where every sentence is an element in the Array
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
