package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.hoff.edu.service.command.handler.ConsoleCommandHandler;

@ShellComponent
@RequiredArgsConstructor
public class ParcelController {

    private final static String CREATE_COMMAND = "/create";
    private final static String EDIT_COMMAND = "/edit";
    private final static String FIND_COMMAND = "/find";
    private final static String DELETE_COMMAND = "/delete";
    private final static String LOAD_COMMAND = "/load";
    private final static String UNLOAD_COMMAND = "/unload";
    private final ConsoleCommandHandler commandHandler;

    @ShellMethod(key = CREATE_COMMAND)
    public void createParcel(@ShellOption(value = "--args") String args) {
        commandHandler.handleCommand(CREATE_COMMAND + " " + args);
    }

    @ShellMethod(key = EDIT_COMMAND)
    public void editParcel(@ShellOption(defaultValue = "") String command) {
        commandHandler.handleCommand(EDIT_COMMAND + " " + command);
    }

    @ShellMethod(key = FIND_COMMAND)
    public void findParcel(@ShellOption(defaultValue = "") String command) {
        commandHandler.handleCommand(FIND_COMMAND + " " + command);
    }

    @ShellMethod(key = DELETE_COMMAND)
    public void deleteParcel(@ShellOption(defaultValue = "") String command) {
        commandHandler.handleCommand(DELETE_COMMAND + " " + command);
    }

    @ShellMethod(key = LOAD_COMMAND)
    public void loadParcel(@ShellOption(defaultValue = "") String command) {
        commandHandler.handleCommand(LOAD_COMMAND + " " + command);
    }

    @ShellMethod(key = UNLOAD_COMMAND)
    public void unloadParcel(@ShellOption(defaultValue = "") String command) {
        commandHandler.handleCommand(UNLOAD_COMMAND + " " + command);
    }
}
