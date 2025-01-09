package ru.hoff.edu.service;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.model.Command;
import ru.hoff.edu.util.filereader.InputFileReader;
import ru.hoff.edu.util.filewriter.FileWriter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class ParcelUnloaderService implements ParcelService {

    private final InputFileReader inputFileReader;
    private final FileWriter<Map<String, Object>> fileWriter;

    public ParcelUnloaderService(InputFileReader inputFileReader, FileWriter<Map<String, Object>> fileWriter) {
        this.inputFileReader = inputFileReader;
        this.fileWriter = fileWriter;
    }

    @Override
    public void processing(Command command) {
        try {
            List<Map<String, Object>> truckDataList = inputFileReader.readFile(command.getInputFile());
            fileWriter.writeToFile(command.getPathToResultFile(), truckDataList);
        } catch (IOException ex) {
            log.error("Error while processing file {}", command.getInputFile(), ex);
        }
    }
}
