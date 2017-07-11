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



import java.io.*;
public class OpenNLP {

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
        SentenceDetectorME sentenceDetector = null;
        InputStream modelIn = null;
        String[] result = null;
        try{
            modelIn = getClass().getResourceAsStream("en-sent.bin");
            final SentenceModel sentenceModel = new SentenceModel(modelIn);
            modelIn.close();
            sentenceDetector = new SentenceDetectorME(sentenceModel);
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
        String senteces[]=(sentenceDetector.sentDetect(input));
        for(String s : senteces){
            System.out.println(s);
        }
    }

    public void Tokenizer(String input) throws FileNotFoundException{
        InputStream modelIn = getClass().getResourceAsStream("en-token.bin");
            try{
                TokenizerModel model = new TokenizerModel(modelIn);
                Tokenizer tokenizer = new TokenizerME(model);
                String[] tokens = tokenizer.tokenize(input);

                for(String t : tokens){
                    System.out.println(t);
                }
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
