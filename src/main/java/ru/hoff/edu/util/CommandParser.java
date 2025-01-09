package ru.hoff.edu.util;

import ru.hoff.edu.model.Command;
import ru.hoff.edu.model.CommandUnload;
import ru.hoff.edu.model.CommandUpload;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;

public class CommandParser {

    private final int REQUIRED_NUMBER_OF_PARAMETERS_TO_UPLOAD = 3;
    private final int REQUIRED_NUMBER_OF_PARAMETERS_TO_UNLOAD = 2;

    public Command parse(String command) {
        String[] commandParts = command.split(" ");
        if (command.contains("upload")) {
            return parseUploadCommand(commandParts);
        }

        if (command.contains("unload")) {
            return parseUnloadCommand(commandParts);
        }

        throw new IllegalArgumentException("Invalid command format");
    }

    private CommandUpload parseUploadCommand(String[] commandParts) {
        if (commandParts.length < REQUIRED_NUMBER_OF_PARAMETERS_TO_UPLOAD) {
            throw new IllegalArgumentException("Invalid command format");
        }

        CommandType type = CommandType.fromString(commandParts[0]);
        AlgorithmType algorithmType = AlgorithmType.fromString(commandParts[1]);

        String inputFile;
        if (!commandParts[2].isEmpty()) {
            inputFile = commandParts[2];
        } else {
            throw new IllegalArgumentException("InputFile path is empty");
        }

        Integer maxTrucksCount;
        if (canParseToInt(commandParts[3])) {
            maxTrucksCount = Integer.parseInt(commandParts[3]);
        } else {
            maxTrucksCount = null;
        }

        String pathToResultFile = null;
        for (int i = 3; i < commandParts.length; i++) {
            if (canParseToInt(commandParts[i])) {
                maxTrucksCount = Integer.valueOf(commandParts[i]);
            } else {
                pathToResultFile = commandParts[i];
            }
        }

        return new CommandUpload(type, algorithmType, inputFile, maxTrucksCount, pathToResultFile);
    }

    private CommandUnload parseUnloadCommand(String[] commandParts) {
        if (commandParts.length < REQUIRED_NUMBER_OF_PARAMETERS_TO_UNLOAD) {
            throw new IllegalArgumentException("Invalid command format");
        }

        CommandType type = CommandType.fromString(commandParts[0]);

        String inputFile;
        if (!commandParts[1].isEmpty()) {
            inputFile = commandParts[1];
        } else {
            throw new IllegalArgumentException("InputFile path is empty");
        }

        String outputFile;
        if (!commandParts[2].isEmpty()) {
            outputFile = commandParts[2];
        } else {
            throw new IllegalArgumentException("OutputFile path is empty");
        }

        return new CommandUnload(type, inputFile, outputFile);
    }

    public static boolean canParseToInt(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
