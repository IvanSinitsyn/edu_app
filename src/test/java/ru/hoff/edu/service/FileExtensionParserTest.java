package ru.hoff.edu.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileExtensionParserTest {

    private final FileExtensionParser fileExtensionParser = new FileExtensionParser();

    @Test
    void getFileExtension_shouldReturnCorrectExtension() {
        assertEquals("txt", fileExtensionParser.getFileExtension("file.txt"));
        assertEquals("json", fileExtensionParser.getFileExtension("data.json"));
        assertEquals("", fileExtensionParser.getFileExtension("noextension"));
    }
}
