package backend;

/**
 * Created by Andy on 18.07.2017
 */

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CacheManager {

    private static final String cachePath = "cache/";
    private static File cacheDir = new File("cache");

    static String getCachePath() {

        return cachePath;
    }

    static void makeCacheDir() {

        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }

    /**
     * Clean the cache-folder
     */
    public static void cleanCache() {

        if (cacheDir.exists()) {
            File[] listOfFiles = cacheDir.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {

                if (listOfFiles[i].isFile()) {

                    File file = listOfFiles[i];
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
                    LocalDateTime now = LocalDateTime.now();

                    if (!file.getName().endsWith(dtf.format(now))) {
                        file.delete();
                    }
                }
            }
        }
    }
}
