package backend;

import backend.exceptions.NoTextFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WebTextProvider extends FileTextProvider implements TextProvider {

    private String title = "";
    private Elements pElements = null;
    private Elements captionElements = null;

    public WebTextProvider(final String url) throws IOException {

        Document document = Jsoup.connect(url).get();
        this.path = url;

        this.title = document.title();
        this.pElements = document.select("p");
        this.captionElements = document.select("div.thumbcaption");

        if (this.pElements == null)
        {
            throw new NoTextFoundException("WebTextProvider could not find any text-content!");
        }

        makeCacheDir();
        saveWebContentToFile();
    }

    private void makeCacheDir()
    {
        File cacheDir = new File("cache");

        if (!cacheDir.exists() || !cacheDir.isDirectory())
        {
            cacheDir.mkdir();
        }
    }

    private void saveWebContentToFile() throws IOException
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDateTime now = LocalDateTime.now();

        this.file = new File("cache/"+title+"_"+dtf.format(now));

        if (!this.file.exists())
        {
            System.out.println("Article not in cache yet");

            BufferedWriter writer = null;
            try
            {
                writer =  new BufferedWriter(new FileWriter(this.file));

                for (Element element : this.pElements)
                {
                    writer.append(element.text());
                }

                if (this.captionElements != null)
                {
                    for (Element element : this.captionElements)
                    {
                        writer.append(element.text());
                    }
                }
            }
            catch (IOException e)
            {
                throw e;
            }
            finally {
                try
                {
                    if (writer != null)
                    {
                        writer.close();
                    }
                }
                catch (IOException e)
                {
                    throw e;
                }
            }
        }
    }
}
