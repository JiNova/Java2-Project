/**
 * Created by Evo on 10.07.2017.
 */

import jdk.internal.util.xml.impl.Input;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import java.io.StringReader;
import java.util.ArrayList;



import java.io.*;
public class POSClass {
    public String text;
    public ArrayList<String[]> sentences;
    public ArrayList<String[]> tkns;
    public ArrayList<String[]> POStags;

    public POSClass(String s){
        text = s;
    }
    public void POSTag() {

        InputStream inputStream = getClass().getResourceAsStream("en-pos-maxent.bin");
        try {
            POSModel model = new POSModel(inputStream);
            POSTaggerME tagger = new POSTaggerME(model);


            for (String[] s : sentences) {
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

    public void sSplitter(text){
        InputStream modelIn = getClass().getResourceAsStream("en-sent.bin");
        try{

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            modelIn.close();
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);


            String[] sent = sentenceDetector.sentDetect(input);
            for(String s : sent){
                sentences.add(s.split("[./?!]"));
            }

        }catch (final IOException e){
            e.printStackTrace();
        }finally{
            if(modelIn != null){
                try{
                    modelIn.close();
                }catch (final IOException e){

                }
            }
        }
    }

    public void Tokenizer() throws FileNotFoundException{
        InputStream modelIn = getClass().getResourceAsStream("en-token.bin");
            try{
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                for(String[] s : sentences){
                    tkns.add(s);
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

    public static void main(String[] args) throws FileNotFoundException {
        POSClass a = new POSClass("This is a test. Does it work? I hope so!");
        a.sSplitter();
        try {
            a.Tokenizer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        a.POSTag();

        for(int i; i< sentences)
    }
}
