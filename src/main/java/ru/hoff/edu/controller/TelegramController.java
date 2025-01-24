package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hoff.edu.config.TgBotConfig;
import ru.hoff.edu.service.handler.impl.TelegramCommandHandler;

/**
 * Класс, реализующий контроллер Telegram-бота.
 * Обрабатывает входящие сообщения от пользователей и отправляет ответы.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramController extends TelegramLongPollingBot {

    private final TgBotConfig tgBotConfig;
    private final TelegramCommandHandler telegramCommandHandler;

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return tgBotConfig.getBotName();
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return tgBotConfig.getBotToken();
    }

    /**
     * Обрабатывает входящие обновления (сообщения) от пользователей.
     *
     * @param update Объект {@link Update}, содержащий входящее сообщение.
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String userMessage = update.getMessage().getText();

        String response = telegramCommandHandler.handle(userMessage);

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
