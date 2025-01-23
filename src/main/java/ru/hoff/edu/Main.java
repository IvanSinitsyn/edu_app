package ru.hoff.edu;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.controller.ConsoleController;
import ru.hoff.edu.controller.TelegramController;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.command.handler.ConsoleCommandHandler;
import ru.hoff.edu.service.command.handler.CreateParcelCommandHandler;
import ru.hoff.edu.service.command.handler.DeleteParcelCommandHandler;
import ru.hoff.edu.service.command.handler.EditParcelCommandHandler;
import ru.hoff.edu.service.command.handler.FindParcelByIdQueryHandler;
import ru.hoff.edu.service.command.handler.LoadParcelsCommandHandler;
import ru.hoff.edu.service.command.handler.TelegramCommandHandler;
import ru.hoff.edu.service.command.handler.UnloadParcelsCommandHandler;
import ru.hoff.edu.service.factory.CommandParserFactory;
import ru.hoff.edu.service.factory.LoadStrategyFactory;
import ru.hoff.edu.service.factory.ReportWriterFactory;
import ru.hoff.edu.util.filereader.FileReaderFactory;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Main {

    public static void main(String[] args) throws TelegramApiException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        ParcelService parcelService = new ParcelService(new ParcelRepository());
        CommandParserFactory commandParserFactory = new CommandParserFactory(new FileReaderFactory());
        ReportWriterFactory reportWriterFactory = new ReportWriterFactory();
        LoadStrategyFactory loadStrategyFactory = new LoadStrategyFactory(parcelService);

        Map<String, Command<?, ? extends BaseCommandDto>> commandHandlers = new HashMap<>();
        commandHandlers.put("/create", new CreateParcelCommandHandler(parcelService));
        commandHandlers.put("/find", new FindParcelByIdQueryHandler(parcelService));
        commandHandlers.put("/edit", new EditParcelCommandHandler(parcelService));
        commandHandlers.put("/delete", new DeleteParcelCommandHandler(parcelService));
        commandHandlers.put("/load", new LoadParcelsCommandHandler(reportWriterFactory, loadStrategyFactory, parcelService));
        commandHandlers.put("/unload", new UnloadParcelsCommandHandler());

        ConsoleCommandHandler consoleCommandHandler = new ConsoleCommandHandler(commandParserFactory, commandHandlers);
        TelegramCommandHandler telegramCommandHandler = new TelegramCommandHandler(commandParserFactory, commandHandlers);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new TelegramController(telegramCommandHandler));

        log.info("Starting application...");

        ConsoleController consoleController = new ConsoleController(consoleCommandHandler);

        consoleController.listen();
    }
}