package ru.hoff.edu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.model.enums.FileType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.JsonFileReportWriter;
import ru.hoff.edu.service.ReportWriter;
import ru.hoff.edu.service.TextReportWriter;
import ru.hoff.edu.service.filereader.InputFileReader;
import ru.hoff.edu.service.filereader.impl.JsonFileReader;
import ru.hoff.edu.service.filereader.impl.TxtFileReader;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.handler.impl.CreateParcelRequestHandler;
import ru.hoff.edu.service.handler.impl.DeleteParcelRequestHandler;
import ru.hoff.edu.service.handler.impl.EditParcelRequestHandler;
import ru.hoff.edu.service.handler.impl.FindAllParcelsRequestHandler;
import ru.hoff.edu.service.handler.impl.FindParcelByIdRequestHandler;
import ru.hoff.edu.service.handler.impl.LoadParcelsRequestHandler;
import ru.hoff.edu.service.handler.impl.UnloadParcelsRequestHandler;
import ru.hoff.edu.service.parser.CommandParser;
import ru.hoff.edu.service.parser.impl.CreateCommandParser;
import ru.hoff.edu.service.parser.impl.DeleteCommandParser;
import ru.hoff.edu.service.parser.impl.EditCommandParser;
import ru.hoff.edu.service.parser.impl.FindByIdCommandParser;
import ru.hoff.edu.service.parser.impl.LoadCommandParser;
import ru.hoff.edu.service.parser.impl.UnloadCommandParser;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.CreateParcelRequest;
import ru.hoff.edu.service.request.impl.DeleteParcelRequest;
import ru.hoff.edu.service.request.impl.EditParcelRequest;
import ru.hoff.edu.service.request.impl.FindAllParcelsRequest;
import ru.hoff.edu.service.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.request.impl.LoadParcelsRequest;
import ru.hoff.edu.service.request.impl.UnloadParcelsRequest;
import ru.hoff.edu.service.strategy.LoadStrategy;
import ru.hoff.edu.service.strategy.impl.EasyLoadStrategy;
import ru.hoff.edu.service.strategy.impl.EqualLoadStrategy;
import ru.hoff.edu.service.strategy.impl.OptimalLoadStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки бинов Spring.
 * <p>
 * Этот класс определяет бины, необходимые для работы приложения, такие как обработчики команд
 * и API для Telegram-бота.
 * </p>
 */
@Configuration
public class AppConfig {

    @Bean
    public Map<Class<? extends Request>, RequestHandler> handlers(
            CreateParcelRequestHandler createParcelRequestHandler,
            EditParcelRequestHandler editParcelRequestHandler,
            DeleteParcelRequestHandler deleteParcelRequestHandler,
            FindAllParcelsRequestHandler findAllParcelsRequestHandler,
            FindParcelByIdRequestHandler findParcelByIdRequestHandler,
            LoadParcelsRequestHandler loadParcelsRequestHandler,
            UnloadParcelsRequestHandler unloadParcelsRequestHandler) {
        Map<Class<? extends Request>, RequestHandler> handlers = new HashMap<>();
        handlers.put(CreateParcelRequest.class, createParcelRequestHandler);
        handlers.put(EditParcelRequest.class, editParcelRequestHandler);
        handlers.put(DeleteParcelRequest.class, deleteParcelRequestHandler);
        handlers.put(FindAllParcelsRequest.class, findAllParcelsRequestHandler);
        handlers.put(FindParcelByIdRequest.class, findParcelByIdRequestHandler);
        handlers.put(LoadParcelsRequest.class, loadParcelsRequestHandler);
        handlers.put(UnloadParcelsRequest.class, unloadParcelsRequestHandler);
        return handlers;
    }

    @Bean
    public Map<FileType, InputFileReader> inputFileReaderMap(
            TxtFileReader txtFileReader,
            JsonFileReader jsonFileReader) {
        Map<FileType, InputFileReader> readers = new HashMap<>();
        readers.put(FileType.TXT, txtFileReader);
        readers.put(FileType.JSON, jsonFileReader);
        return readers;
    }

    @Bean
    public Map<CommandType, CommandParser> commandParsers(
            CreateCommandParser createCommandParser,
            DeleteCommandParser deleteCommandParser,
            EditCommandParser editCommandParser,
            FindByIdCommandParser findByIdCommandParser,
            LoadCommandParser loadCommandParser,
            UnloadCommandParser unloadCommandParser) {
        Map<CommandType, CommandParser> commandParsers = new HashMap<>();
        commandParsers.put(CommandType.CREATE, createCommandParser);
        commandParsers.put(CommandType.FIND, findByIdCommandParser);
        commandParsers.put(CommandType.FIND_ALL, createCommandParser);
        commandParsers.put(CommandType.EDIT, editCommandParser);
        commandParsers.put(CommandType.DELETE, deleteCommandParser);
        commandParsers.put(CommandType.UNLOAD, unloadCommandParser);
        commandParsers.put(CommandType.LOAD, loadCommandParser);

        return commandParsers;
    }

    @Bean
    public Map<AlgorithmType, LoadStrategy> loadStrategies(
            EasyLoadStrategy easyLoadStrategy,
            EqualLoadStrategy equalLoadStrategy,
            OptimalLoadStrategy optimalLoadStrategy) {
        Map<AlgorithmType, LoadStrategy> loadStrategies = new HashMap<>();
        loadStrategies.put(AlgorithmType.EASY, easyLoadStrategy);
        loadStrategies.put(AlgorithmType.EQUALLY, equalLoadStrategy);
        loadStrategies.put(AlgorithmType.OPTIMAL, optimalLoadStrategy);
        return loadStrategies;
    }

    @Bean
    public Map<ResultOutType, ReportWriter> reportWriters(
            TextReportWriter textReportWriter,
            JsonFileReportWriter JsonFileReportWriter) {
        Map<ResultOutType, ReportWriter> reportWriters = new HashMap<>();
        reportWriters.put(ResultOutType.TEXT, textReportWriter);
        reportWriters.put(ResultOutType.FILE, JsonFileReportWriter);
        return reportWriters;
    }

    @Bean
    public ObjectWriter objectWriter() {
        return new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

    /**
     * Создает и возвращает экземпляр TelegramBotsApi.
     * <p>
     * Этот бин используется для регистрации и управления Telegram-ботами.
     * </p>
     *
     * @return Экземпляр TelegramBotsApi.
     * @throws TelegramApiException Если возникает ошибка при создании TelegramBotsApi.
     */
    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }
}
