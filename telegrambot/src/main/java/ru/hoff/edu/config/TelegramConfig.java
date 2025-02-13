package ru.hoff.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.parser.CommandParser;
import ru.hoff.edu.service.parser.CreateCommandParser;
import ru.hoff.edu.service.parser.DeleteCommandParser;
import ru.hoff.edu.service.parser.EditCommandParser;
import ru.hoff.edu.service.parser.FindAllCommandParser;
import ru.hoff.edu.service.parser.FindByIdCommandParser;
import ru.hoff.edu.service.parser.LoadCommandParser;
import ru.hoff.edu.service.parser.UnloadCommandParser;

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
public class TelegramConfig {

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

    @Bean
    public Map<CommandType, CommandParser> commandParsers(
            CreateCommandParser createCommandParser,
            DeleteCommandParser deleteCommandParser,
            EditCommandParser editCommandParser,
            FindByIdCommandParser findByIdCommandParser,
            FindAllCommandParser findAllCommandParser,
            LoadCommandParser loadCommandParser,
            UnloadCommandParser unloadCommandParser) {
        Map<CommandType, CommandParser> commandParsers = new HashMap<>();
        commandParsers.put(CommandType.CREATE, createCommandParser);
        commandParsers.put(CommandType.FIND, findByIdCommandParser);
        commandParsers.put(CommandType.FIND_ALL, findAllCommandParser);
        commandParsers.put(CommandType.EDIT, editCommandParser);
        commandParsers.put(CommandType.DELETE, deleteCommandParser);
        commandParsers.put(CommandType.UNLOAD, unloadCommandParser);
        commandParsers.put(CommandType.LOAD, loadCommandParser);

        return commandParsers;
    }
}
