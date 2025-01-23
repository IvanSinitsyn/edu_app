package ru.hoff.edu.util.filewriter;


import java.io.IOException;
import java.util.List;

public interface OutputFileWriter<T> {

    void write(String filePath, List<T> data) throws IOException;
}

