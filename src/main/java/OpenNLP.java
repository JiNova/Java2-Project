/**
 * Created by Evo on 10.07.2017.
 */

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.*;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.*;
public class OpenNLP {
    POSModel model = null;
    POSTagger posInstance = null;

    public void loadModel() {
        File inputFile = null;
        try {
            String fileLocation = "opennlp/en-pos-maxent.bin";
            if (new File(fileLocation).exists()) {
                inputFile = new File(fileLocation);
            } else {
                System.out.println("File: " + fileLocation + " does not exists.");
            }
            if (inputFile != null) {
                model = new POSModelLoader().load(inputFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTag(String input) {
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
}
