package ru.hoff.edu.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class InputFileParser {

    private final PackageConverter packageConverter;
    private final PackageSorter packageSorter;

    public List<char[][]> parsePackageFromFile(String inputFile) {
        log.info("Start parsing file: {}", inputFile);

        List<char[][]> packages = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            List<String> currentPackage = new ArrayList<>();

            String line;
            while((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    if (!currentPackage.isEmpty()) {
                        packages.add(packageConverter.convertToPackage(currentPackage));
                        currentPackage.clear();
                    }
                } else {
                    log.info("Parsing line: {}", line);
                    currentPackage.add(line);
                }
            }

            if (!currentPackage.isEmpty()) {
                packages.add(packageConverter.convertToPackage(currentPackage));
            }
        } catch (IOException ex) {
            log.error("Error reading input file: {}", ex.getMessage());
        }

        return packageSorter.sortDesc(packages);
    }


}
