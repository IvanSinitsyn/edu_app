package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.enums.Mode;
import ru.hoff.edu.util.InputFileParser;
import ru.hoff.edu.util.InputFileReader;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class CommandHandler {

    private final Pattern IMPORT_FILE_PATTERN = Pattern.compile("import(?:\\s+easy)?\\s+(.+\\.txt)");
    private final InputFileReader inputFileReader;
    private final InputFileParser inputFileParser;
    private final PackageLoaderService packageLoaderService;

    public void handle(String command) {
        Matcher hardMatcher = IMPORT_FILE_PATTERN.matcher(command);
        if  (!hardMatcher.matches()) {
            log.error("Unknown command: {}", command);
            return;
        }

        try {
            String inputFile = hardMatcher.group(1);
            log.info("Start reading inputFile...");
            List<String> fileLines = inputFileReader.readLinesFromFile(inputFile);

            log.info("Start parsing packages...");
            List<char[][]> packages = inputFileParser.parsePackages(fileLines);

            Mode mode = command.contains("easy") ? Mode.EASY : Mode.HARD;
            List<Truck> trucks = packageLoaderService.loadPackagesInTrucks(packages, mode);
            packageLoaderService.makeLoadingReport(trucks);
        } catch (IOException ex) {
            log.error("Error reading input file", ex);
        }
    }
}
