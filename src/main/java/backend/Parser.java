package backend;

/**
 * Created by Evangelos on 10.07.2017.
 */

import backend.exceptions.ModuleNotInitializedException;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Class for all handling of the text, utilizing the OpenNLP-framework
 */
public class Parser {

    private POSTaggerME tagger          = null;
    private TokenizerME tokenizer       = null;
    private LemmatizerME lemmatizer     = null;
    private SentenceDetectorME splitter = null;

    /**
     * Set up the tagger-module
     *
     * @param posModelFile URL of the model-file
     * @throws IOException
     */
    public void setTagger(final URL posModelFile) throws IOException {

        InputStream taggerStream = null;

        try
        {
            //initialize tagger
            taggerStream = posModelFile.openStream();
            POSModel posModel = new POSModel(taggerStream);
            this.tagger = new POSTaggerME(posModel);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally {

            try {
                if (null != taggerStream) {
                    taggerStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Set up the tokenizer-module
     *
     * @param tokenModelFile URL of the model-file
     * @throws IOException
     */
    public void setTokenizer(final URL tokenModelFile) throws IOException {

        InputStream tokenStream = null;

        try {
            //initialize tokenize
            tokenStream = tokenModelFile.openStream();
            TokenizerModel tokenizerModel = new TokenizerModel(tokenStream);
            this.tokenizer = new TokenizerME(tokenizerModel);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally {

            try {

                if (null != tokenStream) {
                    tokenStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Set up the lemmatizer-module
     *
     * @param lemmaModelFile URL of the model-file
     * @throws IOException
     */
    public void setLemmatizer(final URL lemmaModelFile) throws IOException {
        InputStream lemmaStream = null;

        try {

            //initialize lemmatizer
            lemmaStream = lemmaModelFile.openStream();
            LemmatizerModel lemmatizerModel = new LemmatizerModel(lemmaStream);
            this.lemmatizer = new LemmatizerME(lemmatizerModel);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally {

            try {
                if (null != lemmaStream) {
                    lemmaStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Set up the sentence-splitter-module
     *
     * @param splitterModelFile URL of the model-file
     * @throws IOException
     */
    public void setSplitter( final URL splitterModelFile) throws IOException {

        InputStream splitterStream = null;

        try {

            //initialize splitter
            splitterStream = splitterModelFile.openStream();
            SentenceModel sentenceModel = new SentenceModel(splitterStream);
            this.splitter = new SentenceDetectorME(sentenceModel);
        }
        catch (IOException e)
        {
            throw e;
        }
        finally {

            try
            {
                if (null != splitterStream)
                {
                    splitterStream.close();
                }
            }
            catch (IOException e)
            {
                throw e;
            }
        }

    }

    /**
     * Method to get tags of tokens
     *
     * @param input tokens of the text
     * @return ArrayList of StringArrays that is filled with all tags
     * @throws ModuleNotInitializedException
     */
    public String[] getPosTag(String[] input) throws ModuleNotInitializedException {

        if (this.tagger != null)
        {
            return this.tagger.tag(input);
        }
        else
        {
            throw new ModuleNotInitializedException("Tagger-module not initialized!");
        }
    }

    /**
     * Tokenizes a sentence into single words
     *
     * @param input the sentence
     * @return ArrayList of StringArrays which contains all tokens of the sentences
     * @throws ModuleNotInitializedException
     */
    public String[] tokenize(String input) throws ModuleNotInitializedException {

        if (this.tokenizer != null)
        {
            return this.tokenizer.tokenize(input);
        }
        else
        {
            throw new ModuleNotInitializedException("Tokenizer-module not initialized!");
        }
    }

    /**
     * Lemmas of the text
     *
     * @param words
     * @param tags
     * @return
     * @throws ModuleNotInitializedException
     */
    public String[] getLemma(String[] words, String[] tags) throws ModuleNotInitializedException {

        if (this.lemmatizer != null)
        {
            return this.lemmatizer.lemmatize(words, tags);
        }
        else
        {
            throw new ModuleNotInitializedException("Lemmatizer-module not initialized!");
        }
    }

    /**
     * Method to split all sentences in the text to a ArrayList
     *
     * @return ArrayList with all sentences of the text
     * @throws ModuleNotInitializedException
     */
    public String[] splitSentences(final String text) throws ModuleNotInitializedException {

        if (this.splitter != null)
        {
            return this.splitter.sentDetect(text);
        }
        else
        {
            throw new ModuleNotInitializedException("Splitter-module not initialized!");
        }
    }
}
