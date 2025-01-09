package ru.hoff.edu.model;

import lombok.Getter;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;

@Getter
public class CommandUnload implements Command {

    private final CommandType commandType;
    private final String inputFile;
    private final String pathToResultFile;

    public CommandUnload(CommandType commandType, String inputFile, String pathToResultFile) {
        this.commandType = commandType;
        this.inputFile = inputFile;
        this.pathToResultFile = pathToResultFile;
    }

    @Override
    public Integer getMaxTrucksCount() {
        return 0;
    }

    @Override
    public AlgorithmType getAlgorithmType() {
        return null;
    }
}
