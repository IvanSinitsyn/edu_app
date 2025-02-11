package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.ParcelServiceCaller;
import ru.hoff.edu.service.Request;
import ru.hoff.edu.service.factory.CommandParserFactory;

/**
 * Класс, реализующий контроллер Telegram-бота.
 * Обрабатывает входящие сообщения от пользователей и отправляет ответы.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramController extends TelegramLongPollingBot {

    private final ParcelServiceCaller parcelServiceCaller;
    @Value("${tg-bot-configuration.botName}")
    private String botName;
    @Value("${tg-bot-configuration.botToken}")
    private String botToken;
    private final CommandParserFactory commandParserFactory;

    /**
     * Возвращает имя бота.
     *
     * @return Имя бота.
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Возвращает токен бота.
     *
     * @return Токен бота.
     */
    @Override
    public String getBotToken() {
        return botToken;
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

        Request request = commandParserFactory.createCommandParser(CommandType.fromString(userMessage.split(" ")[0])).parse(userMessage);
        try {
            Object response = parcelServiceCaller.call(request);
            sendMessage(chatId, response.toString());
        } catch (Exception ex) {
            sendMessage(chatId, ex.getMessage());
        }

    }

    private void sendMessage(String chatId, String response) {
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
