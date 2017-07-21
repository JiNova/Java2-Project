package backend.exceptions;

/**
 * Created by Andy on 18.07.2017
 */

public class ModuleNotInitializedException extends Exception {

    public ModuleNotInitializedException(final String message)
    {
        super(message);
    }

    public ModuleNotInitializedException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
