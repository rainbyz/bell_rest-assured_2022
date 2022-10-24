package helpers;

import java.io.File;

public class StringUtils {
    public static String getFileNameWithoutExtension(String path) {
        String fileName = new File(path).getName();
        int index = fileName.lastIndexOf('.');
        if (index == -1) {
            return fileName;
        } else {
            return fileName.substring(0, index);
        }
    }
}