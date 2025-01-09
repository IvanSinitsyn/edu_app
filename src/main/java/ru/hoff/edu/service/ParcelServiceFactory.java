package ru.hoff.edu.service;

import ru.hoff.edu.model.Command;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.util.filewriter.FileWriter;
import ru.hoff.edu.util.InputFileParser;
import ru.hoff.edu.util.ParcelConverter;
import ru.hoff.edu.util.ParcelSorter;
import ru.hoff.edu.util.filereader.JsonFileReader;
import ru.hoff.edu.util.filereader.TxtFileReader;
import ru.hoff.edu.util.filewriter.JsonFileWriter;
import ru.hoff.edu.util.filewriter.TxtFileWriter;

public class ParcelServiceFactory {

    public ParcelService createPackageService(Command command) {
        if (command.getCommandType() == CommandType.UNLOAD) {
            return new ParcelUnloaderService(new JsonFileReader(), new TxtFileWriter());
        }

        if (command.getCommandType() == CommandType.UPLOAD) {
            return new ParcelUploaderService(
                    new TxtFileReader(),
                    new InputFileParser(
                            new ParcelConverter(),
                            new ParcelSorter()),
                    new JsonFileWriter()
            );
        }

        throw new IllegalArgumentException("Unknown command type: " + command.getCommandType());
    }
}
