package backend.exceptions;

/**
 * Created by Andy on 18.07.2017
 */

/**
 * Gets thrown, if a parser-module gets called, but has not been set-up
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
