/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Parser
{
    private String text;
    private ArrayList<String[]> tkns;
    private ArrayList<String[]> POStags;

    public Parser(String s){
        text = s;
        tkns = new ArrayList<String[]>();
        POStags = new ArrayList<String[]>();
    }

    /**
     * Method to get tags of tokens
     * @param input tokens of the text
     * @return ArrayList of StringArrays that is filled with all tags
     * @throws FileNotFoundException
     */
    public static String[] getPosTag(String[] input)
    {
        InputStream inputStream = null;

        try
        {
            inputStream = new FileInputStream("en-pos-maxent.bin");
            POSModel model = new POSModel(inputStream);
            POSTaggerME tagger = new POSTaggerME(model);


            return tagger.tag(input);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
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
     * Method to split all sentences in the text to a ArrayList
     * @return ArrayList with all sentences of the text
     * @throws IOException
     */
    private ArrayList<String> sentenceSplitter()throws IOException
    {
        ArrayList<String> sentences = new ArrayList<String>();
        InputStream modelIn = new FileInputStream("en-sent.bin");
        try {

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            String[] sent = sentenceDetector.sentDetect(text);
            for (int i=0; i < sent.length ; i++) {
                sentences.add(sent[i]);

            }
            return sentences;
        }catch(final IOException e){
               e.printStackTrace();
            }finally {
            if (modelIn != null) {
                try {
                    modelIn.close();
                } catch (final IOException e) {

                }
            }
        }return null;
        }

    /**
     * tokenizes all senteces into single words(tokens)
     * @param input the sentences
     * @return  ArrayList of StringArrays which contains all tokens of the sentences
     * @throws FileNotFoundException
     */
    private ArrayList<String[]> tokenizer(ArrayList<String> input) throws FileNotFoundException{
        InputStream modelIn = new FileInputStream("en-token.bin");
            try{
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                    for(int i = 0; i < input.size(); i++){
                        String[] filler = tokenizer.tokenize(input.get(i));
                        tkns.add(filler);
                    }
                return tkns;

            } catch(FileNotFoundException e){
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            } finally{
                if(modelIn != null){
                    try{
                        modelIn.close();
                    }catch (IOException e){
                    }
                }
            }
            return null;
    }

    /**
     * Returns a String of all words of the text in the same order as well as the tags
     * @param tokensToPrint get all tokens of the text
     * @param tagsToPrint   get all tags of the tokens
     * @return a String to see the input text of the beginning with all its tags
     */
//    public String printIt(ArrayList<String[]> tokensToPrint, ArrayList<String[]> tagsToPrint){
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


    public void parse() throws IOException {
//        Parser a = new Parser("This is a test. Does it work? I hope so!");
//
//           ArrayList<String> text = a.sentenceSplitter();
//           ArrayList<String[]> tokens = a.tokenizer(text);
//           ArrayList<String[]> tags = a.getPosTag(tokens);
//           System.out.println(a.printIt(tokens, tags));
    }

    public static Map<String, String> getWordsTag(String[] words)
    {
        Map<String, String> results = new HashMap<String, String>();

        String[] tags = getPosTag(words);

        if (words.length != tags.length)
        {
            //We done goofed
            return null;
        }
        else
        {
            for (int i = 0; i < words.length; i ++)
            {
                results.put(words[i], tags[i]);
            }

            return results;
        }
    }
}
