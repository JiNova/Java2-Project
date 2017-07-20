package backend.exceptions;

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
