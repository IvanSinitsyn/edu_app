package ru.hoff.edu.util.filewriter;


import java.io.IOException;
import java.util.List;

public interface FileWriter<T> {

    void writeToFile(String filePath, List<T> data) throws IOException;
}

