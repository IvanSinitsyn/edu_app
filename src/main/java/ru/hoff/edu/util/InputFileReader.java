package ru.hoff.edu.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InputFileReader {

    public List<String> readLinesFromFile(String inputFile) throws IOException {
        List<String> lines = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        String line;
        while((line = br.readLine()) != null) {
            line = line.trim();
            lines.add(line);
        }

        return lines;
    }
}
