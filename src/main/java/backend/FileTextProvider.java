package backend;

/**
 * Created by Andreas on 16.07.2017
 */

import java.io.*;

/**
 * Loads text-conent from a local file
 */
public class FileTextProvider implements TextProvider {

    String path = "";
    File file = null;

    public FileTextProvider()
    {

    }

    /**
     * Sets up text-provider to provide text from a local file (text-file)
     *
     * @param path Path to the file
     * @throws FileNotFoundException
     */
    public FileTextProvider(final String path) throws FileNotFoundException {

        this.file = new File(path);

        if (!this.file.exists())
        {
            throw new FileNotFoundException();
        }

        this.path = path;
    }

    /**
     * Provides the text-content-stream from the selected text-source
     * @return
     * @throws FileNotFoundException
     */
    @Override
    public FileReader getContentReader() throws FileNotFoundException {

        return new FileReader(this.file);
    }
}
