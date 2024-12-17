package ru.hoff.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.domain.Truck;
import ru.hoff.enums.Mode;
import ru.hoff.service.PackageLoaderService;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
public class ConsoleController {

    private final PackageLoaderService packageLoaderService;
    private final Pattern IMPORT_FILE_PATTERN = Pattern.compile("import(?:\\s+easy)?\\s+(.+\\.txt)");

    public void listen() {
        var scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                System.exit(0);
            }

            Mode mode = command.contains("easy") ? Mode.EASY : Mode.HARD;
            Matcher hardMatcher = IMPORT_FILE_PATTERN.matcher(command);
            if  (hardMatcher.matches()) {
                String filePath = hardMatcher.group(1);
                List<Truck> trucks = packageLoaderService.loadPackages(filePath, mode);
                printInfo(trucks);
            } else {
                log.error("Unknown command: {}", command);
            }
        }
    }

    private void printInfo(List<Truck> trucks) {
        for (int i = 0; i < trucks.size(); i++) {
            System.out.println("Truck " + (i + 1) + ":");
            trucks.get(i).print();
        }
    }
}
