package ru.hoff.edu.model;

import lombok.Getter;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;

@Getter
public class CommandUpload implements Command {

    private final CommandType commandType;
    private final AlgorithmType algorithmType;
    private final String inputFile;
    private final Integer maxTrucksCount;
    private final String pathToResultFile;

    public CommandUpload(CommandType commandType, AlgorithmType algorithmType, String inputFile, Integer maxTrucksCount, String pathToResultFile) {
        this.commandType = commandType;
        this.algorithmType = algorithmType;
        this.inputFile = inputFile;
        this.maxTrucksCount = maxTrucksCount;
        this.pathToResultFile = pathToResultFile;
    }
}
