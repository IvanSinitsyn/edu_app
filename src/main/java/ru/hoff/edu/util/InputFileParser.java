package ru.hoff.edu.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class InputFileParser {

    private final PackageConverter packageConverter;
    private final PackageSorter packageSorter;

    public List<char[][]> parsePackages(List<String> fileLines) {
        List<char[][]> packages = new ArrayList<>();
        List<String> currentPackage = new ArrayList<>();
        for (String fileLine : fileLines) {
            if (fileLine.isEmpty()) {
                if (!currentPackage.isEmpty()) {
                    packages.add(packageConverter.convertToPackage(currentPackage));
                    currentPackage.clear();
                }
            } else {
                log.info("Parsing line: {}", fileLine);
                currentPackage.add(fileLine);
            }
        }

        if (!currentPackage.isEmpty()) {
            packages.add(packageConverter.convertToPackage(currentPackage));
        }

        return packageSorter.sortDesc(packages);
    }
}
