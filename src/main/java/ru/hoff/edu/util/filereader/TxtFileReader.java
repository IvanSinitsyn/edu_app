package ru.hoff.edu.util.filereader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtFileReader implements InputFileReader {

    @Override
    public List<Map<String, Object>> readFile(String inputFile) throws IOException {
        List<Map<String, Object>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            Map<String, Object> currentParcel = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("Name:")) {
                    if (currentParcel != null) {
                        data.add(currentParcel);
                    }
                    currentParcel = new HashMap<>();
                    handleName(line, currentParcel);
                }
                else if (line.startsWith("Form:")) {
                    handleForm(br, line, currentParcel);
                }
                else if (line.startsWith("Symbol:")) {
                    handleSymbol(line, currentParcel);
                }
            }

            if (currentParcel != null) {
                data.add(currentParcel);
            }
        }
        return data;
    }

    private void handleName(String line, Map<String, Object> currentParcel) {
        currentParcel.put("name", line.substring(5).trim());
    }

    private void handleForm(BufferedReader br, String line, Map<String, Object> currentParcel) throws IOException {
        List<String> formLines = new ArrayList<>();
        String formInFirstLine = line.substring(5).trim();
        if (!formInFirstLine.isEmpty()) {
            formLines.add(formInFirstLine);
        }

        String nextLine;
        while ((nextLine = br.readLine()) != null && !nextLine.startsWith("Symbol:") && !nextLine.startsWith("Name:") && !nextLine.isEmpty()) {
            formLines.add(nextLine.trim());
        }

        currentParcel.put("form", formLines);

        if (nextLine != null && nextLine.startsWith("Symbol:")) {
            handleSymbol(nextLine, currentParcel);
        }
    }

    private void handleSymbol(String line, Map<String, Object> currentParcel) {
        currentParcel.put("symbol", line.substring(7).trim());
    }
}
