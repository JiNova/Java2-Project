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
        sentences = new ArrayList<String>();
        tkns = new ArrayList<String[]>();
        POStags = new ArrayList<String[]>();
    }


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

    public static void main(String[] args) {
        POSClass a = new POSClass("This is a test. Does it work? I hope so!");

        try {
           ArrayList<String> text = a.sentenceSplitter();
           ArrayList<String[]> tokens = a.tokenizer(text);
           ArrayList<String[]> tags = a.posTag(tokens);

            for(int i= 0; i < text.size() ;i++){
                String[] runner = tokens.get(i);
                String[] rnr = tags.get(i);
                for(int j = 0; j < tokens.get(i).length; j++){

                    System.out.print(runner[j]+"_");
                    System.out.print(rnr[j]+" ");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
