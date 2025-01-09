package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.hoff.edu.model.Command;
import ru.hoff.edu.util.CommandParser;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CommandHandler {

    private final String HELP_COMMAND = "help";
    private final CommandParser commandParser;
    private final ParcelServiceFactory parcelServiceFactory;

    public void handle(String commandString) {
        if (HELP_COMMAND.equals(commandString)) {
            printHelp();
            return;
        }

        try {
            Command command = commandParser.parse(commandString);
            ParcelService parcelService = parcelServiceFactory.createPackageService(command);
            parcelService.processing(command);
        } catch (IOException ex) {
            log.error("Error reading input file", ex);
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private void printHelp() {
        System.out.println("Команда для погрузки посылок: upload {algorithm} {path_to_file} {maxTrucksCount} {saveResultToFile}");
        System.out.println("Параметры:");
        System.out.println("\talgorithm:");
        System.out.println("\t\teasy - одна посылка в одну машину");
        System.out.println("\t\tequally - машины загружаются равномерно");
        System.out.println("\t\toptimal - каждая машина загружается полностью;");
        System.out.println("\tpath_to_file(необязательный): путь к файлу с посылками в формате txt");
        System.out.println("\tmaxTrucksCount(необязательный): максимально кол-во грузовиков для погрузки посылок");
        System.out.println("\tsaveResultToFile(необязательный): при использовании результат погрузки сохраняется в файл в формате json");

        System.out.println("Команда для разгрузки посылок: unload {path_to_file} {path_to_result_file}");
        System.out.println("Параметры:");
        System.out.println("\tpath_to_file: путь к файлу с загруженными грузовиками в формате json");
        System.out.println("\tpath_to_result_file: путь к файлу с разгруженными посылками в формате txt");
    }
}
