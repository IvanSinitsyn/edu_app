package ru.hoff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.hoff.controller.ConsoleController;
import ru.hoff.service.PackageLoaderService;
import ru.hoff.util.TxtParser;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Starting application...");
        Main.start();
    }

    private static void start() {
        ConsoleController consoleController = new ConsoleController(
                new PackageLoaderService(new TxtParser()));
        consoleController.listen();
    }
}