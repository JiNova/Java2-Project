package backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
    public InputStream getContentStream() throws FileNotFoundException {

        return new FileInputStream(this.file);
    }
}
