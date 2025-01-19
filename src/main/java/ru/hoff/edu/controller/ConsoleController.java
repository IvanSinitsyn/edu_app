package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import ru.hoff.edu.service.command.handler.ConsoleCommandHandler;

import java.util.Scanner;

@RequiredArgsConstructor
public class ConsoleController {

    private static final String EXIT_COMMAND = "exit";
    private final ConsoleCommandHandler consoleCommandHandler;

    public void listen() {
        var scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            String command = scanner.nextLine();
            if (EXIT_COMMAND.equals(command)) {
                System.exit(0);
            }

            consoleCommandHandler.handleCommand(command);
        }
    }
}
