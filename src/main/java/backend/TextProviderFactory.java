package backend;

import java.io.IOException;

public class TextProviderFactory {

    public static enum PROVIDER_TYPES {

        FILE,
        WEB
    }

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
