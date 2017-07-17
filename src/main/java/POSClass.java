/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.lemmatizer.Lemmatizer;
import opennlp.tools.lemmatizer.LemmatizerME;
import opennlp.tools.lemmatizer.LemmatizerModel;
import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;
import opennlp.tools.util.InvalidFormatException;

import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;
import java.util.List;

public class POSClass {
    private String text;
    private ArrayList<String> sentences;
    private ArrayList<String[]> tkns;
    private ArrayList<String[]> POStags;

    public POSClass(String s){
        text = s;
        sentences = new ArrayList<String>();
        tkns = new ArrayList<String[]>();
        POStags = new ArrayList<String[]>();
    }

    /**
     * Method to get tags of tokens
     * @param input tokens of the text
     * @return ArrayList of StringArrays that is filled with all tags
     * @throws FileNotFoundException
     */
    public ArrayList<String[]> posTag(ArrayList<String[]> input) throws FileNotFoundException {

        InputStream inputStream = new FileInputStream("en-pos-maxent.bin");
        try {
            POSModel model = new POSModel(inputStream);
            POSTaggerME tagger = new POSTaggerME(model);


            for (String[] s : input) {
                String tags[] = tagger.tag(s);
                POStags.add(tags);
            }
        return POStags;
        }catch (IOException e) {
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
     * Method to split all sentences in the text to a ArrayList
     * @return ArrayList with all sentences of the text
     * @throws IOException
     */
    public ArrayList<String> sentenceSplitter()throws IOException {
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
    public ArrayList<String[]> tokenizer(ArrayList<String> input) throws FileNotFoundException{
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
    public String printIt(ArrayList<String[]> tokensToPrint, ArrayList<String[]> tagsToPrint){
        String result = "";

        if(tokensToPrint != null && tagsToPrint!=null){
            for(int i=0;i < sentences.size();i++){
                for(int j=0;j<tokensToPrint.get(i).length;j++){
                    result += tokensToPrint.get(i).clone()[j] + "_" + tagsToPrint.get(i).clone()[j] + " ";
                }
            }
            return result;
        }
        return null;
    }

    /**
     * Method to fill a HashMap with our tokens and tags
     * @param tokensHashMap tokens of our text
     * @param tagsHashMap   POStags of the the tokens
     * @return a hashMap with tokens as keys and tags as values
     */
    public HashMap<String, ArrayList<String>> fillHashMap(ArrayList<String[]> tokensHashMap, ArrayList<String[]> tagsHashMap){

        HashMap<String, ArrayList<String>> resultMap = new HashMap<String, ArrayList<String>>(150);

        if(tokensHashMap != null && tagsHashMap != null){
            for(int i=0;i < sentences.size();i++){
                for(int j=0;j < tokensHashMap.get(i).length;j++){
                    String key = tokensHashMap.get(i).clone()[j];
                    String value = tagsHashMap.get(i).clone()[j];
                    if(resultMap.containsKey(tokensHashMap.get(i).clone()[j])) {
                        resultMap.get(key).add(value);
                    }else{
                        ArrayList<String> valueList = new ArrayList<String>();
                        valueList.add(value);
                        resultMap.put(key, valueList);
                    }
                }
            }
            return resultMap;
        }
        return null;
    }

    public String[] dictLemmatizer(ArrayList<String[]> tokenInput, ArrayList<String[]> tagsInput) throws FileNotFoundException {
        InputStream modelIn = new FileInputStream("en-lemmatizer.bin");
        String[] lemmas = null;
        try {
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(modelIn);
            if(tokenInput != null && tagsInput != null) {
                for (int i = 0; i < tokenInput.size(); i++) {
                    String[] tokenArray = tokenInput.get(i);
                    String[] tagArray = tagsInput.get(i);
                    lemmas = lemmatizer.lemmatize(tokenArray, tagArray);
                }
                return lemmas;
            }
            else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
   /*
    public String dictLemma(String tokenSt, String tagSt)throws FileNotFoundException{
        InputStream modelIn = new FileInputStream("en-lemmatizer.bin");
        String lemma="";
        try{
            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(modelIn);
            lemma = lemmatizer.lemmatize(tokenSt, tagSt);
        }catch(IOException e){
            e.printStackTrace();
        }

    }*/

    public static void main(String[] args) {
        POSClass a = new POSClass("This is a test. Does it work? I hope so!");

        try {
           ArrayList<String> text = a.sentenceSplitter();
           ArrayList<String[]> tokens = a.tokenizer(text);
           ArrayList<String[]> tags = a.posTag(tokens);
           System.out.println(a.printIt(tokens, tags));

           
           HashMap<String, ArrayList<String>> hashTest = a.fillHashMap(tokens, tags);


           /*OK so we Have a HashMap with all tokens and tags, but printing it doesn't help us
            the way the map is getting filled is completely random*/
           for (String name: hashTest.keySet()){

                String key =name.toString();
                String value = hashTest.get(name).toString();
                System.out.print(key + "_" + value.substring(1, value.length()- 1) + " ");


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
