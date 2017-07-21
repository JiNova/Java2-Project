package backend;

/**
 * Created by Andy on 16.07.2017
 */

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * TextProvider interface
 */
public interface TextProvider {

    FileReader getContentReader() throws FileNotFoundException;
}
