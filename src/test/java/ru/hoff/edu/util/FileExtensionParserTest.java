package ru.hoff.edu.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.hoff.edu.util.FileExtensionParser.getFileExtension;

class FileExtensionParserTest {

    @Test
    void getFileExtension_shouldReturnCorrectExtension() {
        assertEquals("txt", getFileExtension("file.txt"));
        assertEquals("json", getFileExtension("data.json"));
        assertEquals("", getFileExtension("noextension"));
    }
}
