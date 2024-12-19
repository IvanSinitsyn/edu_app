package ru.hoff.edu;

import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.controller.ConsoleController;
import ru.hoff.edu.service.PackageLoaderService;
import ru.hoff.edu.util.PackageConverter;
import ru.hoff.edu.util.PackageSorter;
import ru.hoff.edu.util.InputFileParser;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Starting application...");

        ConsoleController consoleController = new ConsoleController(
                new PackageLoaderService(
                        new InputFileParser(
                                new PackageConverter(),
                                new PackageSorter())));

        consoleController.listen();
    }
}