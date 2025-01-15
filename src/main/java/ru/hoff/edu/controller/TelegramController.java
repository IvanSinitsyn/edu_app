package ru.hoff.edu.controller;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hoff.edu.config.TelegramBotConfig;
import ru.hoff.edu.service.command.handler.TelegramCommandHandler;

@Slf4j
public class TelegramController extends TelegramLongPollingBot {

    private final TelegramBotConfig botConfig;
    private final TelegramCommandHandler telegramCommandHandler;

    public TelegramController(TelegramBotConfig botConfig, TelegramCommandHandler telegramCommandHandler) {
        this.botConfig = botConfig;
        this.telegramCommandHandler = telegramCommandHandler;
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String userMessage = update.getMessage().getText();

        String response = telegramCommandHandler.handleCommand(userMessage);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(response);

        try {
            execute(message);
        } catch (TelegramApiException ex) {
            log.error("Error while executing command", ex);
        }
    }
}
