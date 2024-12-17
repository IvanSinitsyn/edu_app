package ru.hoff.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class TxtParser {

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
                        packages.add(convertToPackage(currentPackage));
                        currentPackage.clear();
                    }
                } else {
                    log.info("Parsing line: {}", line);
                    currentPackage.add(line);
                }
            }

            if (!currentPackage.isEmpty()) {
                packages.add(convertToPackage(currentPackage));
            }
        } catch (IOException ex) {
            log.error("Error reading input file: {}", ex.getMessage());
        }

        packages.sort((a, b) -> {
            int compareSize = Integer.compare(b.length, a.length);
            if (compareSize != 0) {
                return compareSize;
            }

            int sumA = Arrays.stream(a)
                    .mapToInt(arr -> (int) IntStream.range(0, arr.length)
                            .filter(i -> arr[i] != ' ')
                            .count())
                    .sum();

            int sumB = Arrays.stream(b)
                    .mapToInt(arr -> (int) IntStream.range(0, arr.length)
                            .filter(i -> arr[i] != ' ')
                            .count())
                    .sum();
            return Integer.compare(sumB, sumA);
        });

        return packages;
    }

    private char[][] convertToPackage(List<String> packageLines) {
        int height = packageLines.size();
        int width = 0;

        for (String line : packageLines) {
            if (line.length() > width) {
                width = line.length();
            }
        }

        char[][] packageShape = new char[height][width];
        for (int i = 0, reversedI = height - 1; i < height && reversedI >= 0; i++, reversedI--) {
            for (int j = 0; j < width; j++) {
                if (j < packageLines.get(reversedI).length()) {
                    packageShape[i][j] = packageLines.get(reversedI).charAt(j);
                } else {
                    packageShape[i][j] = ' ';
                }
            }
        }

        log.info("Found package: {}", (Object) packageShape);
        return packageShape;
    }
}
