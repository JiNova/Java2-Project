package backend;

/**
 * Created by Andy on 16.07.2017
 */

import java.io.*;

public class FileTextProvider implements TextProvider {

    String path = "";
    File file = null;

    public FileTextProvider()
    {

    }

    public FileTextProvider(final String path) throws FileNotFoundException {

        this.file = new File(path);

        if (!this.file.exists())
        {
            throw new FileNotFoundException();
        }

        this.path = path;
    }

    @Override
    public FileReader getContentReader() throws FileNotFoundException {

        return new FileReader(this.file);
    }
}
