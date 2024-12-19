package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.service.PackageLoaderService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final PackageLoaderService packageLoaderService;
    private final Pattern IMPORT_FILE_PATTERN = Pattern.compile("import(?:\\s+easy)?\\s+(.+\\.txt)");
    private final String EXIT_COMMAND = "exit";

    public void listen() {
        var scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (EXIT_COMMAND.equals(command)) {
                System.exit(0);
            }

            Matcher hardMatcher = IMPORT_FILE_PATTERN.matcher(command);
            if  (hardMatcher.matches()) {
                String filePath = hardMatcher.group(1);
                packageLoaderService.packageProcessingFromFile(filePath, command.contains("easy"));
            } else {
                log.error("Unknown command: {}", command);
            }
        }
    }
}
