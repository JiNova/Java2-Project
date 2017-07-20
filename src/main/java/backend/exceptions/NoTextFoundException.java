package backend.exceptions;

import java.io.IOException;

public class NoTextFoundException extends IOException {

    public NoTextFoundException(final String message)
    {
        super(message);
    }

    public NoTextFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
