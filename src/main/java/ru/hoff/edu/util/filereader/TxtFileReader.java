package ru.hoff.edu.util.filereader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TxtFileReader implements InputFileReader {

    @Override
    public List<String> readFile(String inputFile) {
        List<String> parcelNames = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    parcelNames.add(line.trim());
                }
            }
        } catch (FileNotFoundException e) {
            log.error("File with parcel names not found", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.error("Error while reading parcel names", e);
            throw new RuntimeException(e);
        }

        return parcelNames;
    }
}
