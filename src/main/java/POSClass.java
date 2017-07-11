/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;

import java.util.ArrayList;
import java.io.*;

public class POSClass {
    public String text;
    public ArrayList<String> sentences;
    public ArrayList<String[]> tkns;
    public ArrayList<String[]> POStags;

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

    public void POSTag() {

        InputStream inputStream = getClass().getResourceAsStream("/en-pos-maxent.bin");
        try {
            POSModel model = new POSModel(inputStream);
            POSTaggerME tagger = new POSTaggerME(model);


            for (String s : sentences) {
                String tags[] = tagger.tag(s);
                POStags.add(tags);
            }

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
    }

    public void sentenceSplitter()throws IOException {
        InputStream modelIn = getClass().getResourceAsStream("/en-sent.bin");
        try {

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);

            String[] sent = sentenceDetector.sentDetect(text);
            for (String s : sent) {
                sentences.add(s);
            }

        }catch(final IOException e){
                e.printStackTrace();
            }finally{
                if (modelIn != null) {
                    try {
                        modelIn.close();
                    } catch (final IOException e) {

                    }
                }
            }
        }


    public void Tokenizer() throws FileNotFoundException{
        InputStream modelIn = getClass().getResourceAsStream("/en-token.bin");
            try{
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                for(String[] s : sentences){
                    for(int i = 0; i < s.length; i++){
                        String r = s[i];
                        String[] filler = tokenizer.tokenize(r);
                        tkns.add(filler);
                    }
                }

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
    }

    public static void main(String[] args) {
        POSClass a = new POSClass("This is a test. Does it work? I hope so!");

        try {
            a.sentenceSplitter();
            a.Tokenizer();
            a.POSTag();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String[]> mySen = a.getSentences();
        for(int i= 0; i < mySen.size() ;i++){
            System.out.println(mySen.get(i).toString());
        }
    }
}
