package ru.hoff.edu;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.controller.ConsoleController;
import ru.hoff.edu.service.CommandBot;
import ru.hoff.edu.service.CommandHandler;
import ru.hoff.edu.service.ParcelServiceFactory;
import ru.hoff.edu.util.CommandParser;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public class Main {

    public static void main(String[] args) throws TelegramApiException {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        //test
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new CommandBot());
        //test

        log.info("Starting application...");

        ConsoleController consoleController = new ConsoleController(
                new CommandHandler(new CommandParser(), new ParcelServiceFactory()));

        consoleController.listen();
    }
}