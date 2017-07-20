package backend;

import java.io.FileNotFoundException;
import java.io.InputStream;

public interface TextProvider {

    InputStream getContentStream() throws FileNotFoundException;
}
