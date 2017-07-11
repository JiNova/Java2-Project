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
public class POSTagger {

    public ArrayList<String[]> sentences;
    public String[] tkns;

    public void POSTag(String input)throws IOException{

        InputStream inputStream = new FileInputStream("en-pos-maxent.bin");
        POSModel model = new POSModel(inputStream);
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        if(input != null) {
            perfMon.start();
            String whiteSpaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(input);
            String tags[] = tagger.tag(whiteSpaceTokenizerLine);

            POSSample sample = new POSSample(whiteSpaceTokenizerLine, tags);
            System.out.println(sample.toString());
            perfMon.incrementCounter();
            perfMon.stopAndPrintFinalResult();
        }
    }

    public void SentenceSplitter(String input){
        InputStream modelIn = getClass().getResourceAsStream("en-sent.bin");
        try{

            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            modelIn.close();
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);


            String[] sent = sentenceDetector.sentDetect(input);

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

    public void Tokenizer(String input) throws FileNotFoundException{
        InputStream modelIn = getClass().getResourceAsStream("en-token.bin");
            try{
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                tkns = tokenizer.tokenize(input);

            }catch(IOException e){
                e.printStackTrace();
            }
            finally{
                if(modelIn != null){
                    try{
                        modelIn.close();
                    }catch (IOException e){
                    }
                }
            }
    }
}
