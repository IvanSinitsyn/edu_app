package ru.hoff.edu.service;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class CommandBot extends TelegramLongPollingBot {

    private final String botUsername = "SiivEduAppBot";
    private final String botToken = "";
    private final CommandService commandService = new CommandService();

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String userMessage = update.getMessage().getText();

        String response = commandService.handleCommand(userMessage);

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
