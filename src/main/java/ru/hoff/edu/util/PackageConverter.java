package ru.hoff.edu.util;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class PackageConverter {

    public char[][] convertToPackage(List<String> packageLines) {
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
