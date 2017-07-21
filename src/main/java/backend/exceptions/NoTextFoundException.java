package backend.exceptions;

/**
 * Created by Andy on 16.07.2017
 */

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
