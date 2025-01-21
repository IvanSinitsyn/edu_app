package ru.hoff.edu.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import ru.hoff.edu.controller.TelegramController;

@Slf4j
@Component
public class TelegramBotInitializer implements ApplicationRunner {

    private final TelegramBotsApi botsApi;
    private final TelegramController telegramController;

    @Autowired
    public TelegramBotInitializer(TelegramBotsApi botsApi, TelegramController telegramController) {
        this.botsApi = botsApi;
        this.telegramController = telegramController;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        botsApi.registerBot(telegramController);
        log.info("Telegram bot registered successfully.");
    }
}
