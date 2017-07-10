/**
 * Created by Evo on 10.07.2017.
 */
import opennlp.tools.sentdetect.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SentenceDetector {

    SentenceModel model = null;

    public void loadModel(){
        try{
            File inputFile = null;
            String filelocation = "opennlp/en-sent.bin";
            if(new File(filelocation).exists()){
                inputFile = new File(filelocation);
            }else{
                System.out.println("File: " + filelocation + " does not exists.");
            }
            if(inputFile != null){
                InputStream inputStream = new FileInputStream(inputFile);
                model = new SentenceModel(inputStream);
                inputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sentenceDetect(String input){
        try{
            if(model != null){
                SentenceDetectorME sentenceDetectorME = new SentenceDetectorME(model);
                String[] sentences = sentenceDetectorME.sentDetect(input);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
