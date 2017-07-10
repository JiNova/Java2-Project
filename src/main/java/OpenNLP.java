/**
 * Created by Evo on 10.07.2017.
 */

import jdk.internal.util.xml.impl.Input;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.*;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.*;
public class OpenNLP {
    POSModel model = null;
    POSTagger posInstance = null;


    public void POSTags(String input) {
        try {
            if (model != null) {
                POSTaggerME taggerME = new POSTaggerME(model);
                if (taggerME != null) {
                    String sentences = getSenteces(input);
                    for (String sentence : sentences) {
                        System.out.println("sentence : " + sentence);
                    }
                    for (String sentence : sentences) {
                        String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
                        String[] tags = taggerME.tag(whitespaceTokenizerLine);
                        for (int i = 0; i < whitespaceTokenizerLine.length; i++) {
                            String word = whitespaceTokenizerLine[i].trim();
                            String tag = tags[i].trim();
                            System.out.println(word + " [" + tag + "]");
                        }
                    }
                }
                taggerME = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SentenceSplitter(String input){
        SentenceDetectorME sentenceDetector = null;
        InputStream modelIn = null;

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
