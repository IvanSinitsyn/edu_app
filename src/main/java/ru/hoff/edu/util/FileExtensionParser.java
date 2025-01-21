package ru.hoff.edu.util;

public class FileExtensionParser {

    private final static int INDENT_INDEX_FOR_EXTENSION = 1;

    public static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + INDENT_INDEX_FOR_EXTENSION);
    }
}
