package ru.hoff.edu.model;

import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;

public interface Command {

    CommandType getCommandType();
    String getInputFile();
    Integer getMaxTrucksCount();
    AlgorithmType getAlgorithmType();
    String getPathToResultFile();
}
