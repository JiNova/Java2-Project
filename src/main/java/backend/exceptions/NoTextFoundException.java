package backend.exceptions;

/**
 * Created by Andreas on 16.07.2017
 */

import java.io.IOException;

/**
 * Gets thrown if the WebTextProvider cannot find any text-content on a provided wikipedia-link
 */
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
