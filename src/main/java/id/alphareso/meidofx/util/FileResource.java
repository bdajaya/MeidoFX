package id.alphareso.meidofx.util;

import java.io.InputStream;
import java.net.URL;

public class FileResource {
    private FileResource() {}

    private static URL loadURL(String path) {
        return FileResource.class.getResource(path);
    }

    public static String load (String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return FileResource.class.getResourceAsStream(name);
    }
}
