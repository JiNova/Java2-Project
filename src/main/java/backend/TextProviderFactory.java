package backend;

/**
 * Created by Andy on 16.07.2017
 */

import java.io.IOException;

public class TextProviderFactory {

    public static enum PROVIDER_TYPES {

        FILE,
        WEB
    }

    /**
     * Returns the wanted TextProvider-instance
     *
     * @param path Path/url to the file/webpage, the text-provider should provide
     * @param type TextProvider-type, file or web
     * @return
     * @throws IOException
     */
    public static TextProvider getTextProvider(final String path, final PROVIDER_TYPES type) throws IOException {

        switch (type)
        {
            case FILE:
                return new FileTextProvider(path);

            case WEB:
                return new WebTextProvider(path);

            default:
                return null;
        }
    }
}
