/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;

import java.util.ArrayList;
import java.io.*;

public class POSClass {
    private String text;
    private ArrayList<String> sentences;
    private ArrayList<String[]> tkns;
    private ArrayList<String[]> POStags;

    public POSClass(String s){
        text = s;
    }

    public ArrayList<String> getSentences(){
        return sentences;
    }

    public ArrayList<String[]> getPOStags() {
        return POStags;
    }

    public ArrayList<String[]> getTkns() {
        return tkns;
    }

    public ArrayList<String[]> POSTag(ArrayList<String[]> input) {

        InputStream inputStream = getClass().getResourceAsStream("/en-pos-maxent.bin");
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

    public ArrayList<String> sentenceSplitter()throws IOException {
        InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin");
        try {

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            String[] sent = sentenceDetector.sentDetect(text);
            for (int i=0; i < sent.length ; i++) {
                sentences.add(sent[i]);
                return sentences;
            }

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


    public ArrayList<String[]> tokenizer(ArrayList<String> input) throws FileNotFoundException{
        InputStream modelIn = getClass().getResourceAsStream("/en-token.bin");
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

    public static void main(String[] args) {
        POSClass a = new POSClass("This is a test. Does it work? I hope so!");

        try {
           ArrayList<String> text = a.sentenceSplitter();
           ArrayList<String[]> tokens = a.tokenizer(text);
           ArrayList<String[]> tags = a.POSTag(tokens);

            for(int i= 0; i < text.size() ;i++){
                for(int j = 0; j < text.get(i).length(); j++){
                    String[] runner = tokens.get(i);
                    String[] rnr = tags.get(i);
                    System.out.println(runner[j]);
                    System.out.println(rnr[j]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
