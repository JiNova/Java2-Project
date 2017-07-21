package backend;

/**
 * Created by Andy on 16.07.2017
 */

import java.io.FileNotFoundException;
import java.io.FileReader;

public interface TextProvider {

    FileReader getContentReader() throws FileNotFoundException;
}
