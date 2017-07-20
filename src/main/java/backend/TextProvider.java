package backend;

import java.io.FileNotFoundException;
import java.io.FileReader;

public interface TextProvider {

    FileReader getContentReader() throws FileNotFoundException;
}
