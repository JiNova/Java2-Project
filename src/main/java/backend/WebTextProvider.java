package backend;

/**
 * Created by Andy on 16.07.2017
 */

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

/**
 * Parses a website with Jsoup and saves it as a local file, utilizing the cache manager
 * Is optimized to work with wikipedia (english)
 */
public class WebTextProvider extends FileTextProvider implements TextProvider {

    private String title = "";
    private Elements pElements = null;
    private Elements captionElements = null;

    /**
     * Load text-content from a website (wikipedia) and saves it to a local file
     *
     * @param url The link to the website
     * @throws IOException
     */
    public WebTextProvider(final String url) throws IOException {

        Document document = Jsoup.connect(url).get();
        this.path = url;

        this.title = document.title();
        this.pElements = document.select("div.mw-parser-output > p,div.mw-parser-output > ul");

        this.captionElements = document.select("div.thumbcaption");

        if (this.pElements == null) {
            throw new NoTextFoundException("WebTextProvider could not find any text-content!");
        }

        CacheManager.makeCacheDir();
        saveWebContentToFile();
    }

    private void saveWebContentToFile() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDateTime now = LocalDateTime.now();

        this.file = new File(CacheManager.getCachePath() + title + "_" + dtf.format(now));

        if (!this.file.exists()) {

            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(this.file));

                for (Element element : this.pElements) {

                    writer.append(element.text().replaceAll("\\[(\\d)+?]", "") + "\n");
                }

                if (this.captionElements != null) {
                    for (Element element : this.captionElements) {
                        writer.append(element.text().replaceAll("\\[(\\d)+?]", "") + "\n");
                    }
                }
            }
            catch (IOException e) {
                throw e;
            }
            finally {
                try {
                    if (writer != null) {
                        writer.close();
                    }
                }
                catch (IOException e) {
                    throw e;
                }
            }
        }
    }
}
