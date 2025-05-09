package id.alphareso.meidofx.util;

import java.io.InputStream;
import java.net.URL;

public class FileResource {
    private FileResource() {}

    // Contoh: Selalu asumsikan path dari root classpath
    private static URL loadURLInternal(String path) {
        String adjustedPath = path.startsWith("/") ? path : "/" + path;
        return FileResource.class.getResource(adjustedPath);
    }

    public static String load(String path) {
        URL resourceUrl = loadURLInternal(path);
        return (resourceUrl != null) ? resourceUrl.toString() : null; // Handle null
    }

    public static InputStream loadStream(String name) {
        String adjustedName = name.startsWith("/") ? name : "/" + name;
        return FileResource.class.getResourceAsStream(adjustedName);
    }
}
