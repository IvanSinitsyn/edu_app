package ru.hoff.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.command.impl.CreateParcelCommand;
import ru.hoff.edu.service.command.impl.DeleteParcelCommand;
import ru.hoff.edu.service.command.impl.EditParcelCommand;
import ru.hoff.edu.service.command.impl.FindAllParcelsQuery;
import ru.hoff.edu.service.command.impl.FindParcelByIdQuery;
import ru.hoff.edu.service.command.impl.LoadParcelsCommand;
import ru.hoff.edu.service.command.impl.UnloadParcelsCommand;

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

    /**
     * Создает и возвращает карту обработчиков команд.
     * <p>
     * Каждый обработчик команды связан с определенным типом команды (например, CREATE, FIND, EDIT и т.д.).
     * Эта карта используется для выбора соответствующего обработчика в зависимости от типа команды.
     * </p>
     *
     * @param createParcelCommand  Обработчик для команды создания посылки.
     * @param findParcelByIdQuery  Обработчик для команды поиска посылки по ID.
     * @param editParcelCommand    Обработчик для команды редактирования посылки.
     * @param deleteParcelCommand  Обработчик для команды удаления посылки.
     * @param loadParcelsCommand   Обработчик для команды загрузки посылок.
     * @param unloadParcelsCommand Обработчик для команды выгрузки посылок.
     * @return Карта, связывающая тип команды с соответствующим обработчиком.
     */
    @Bean
    public Map<CommandType, Command<?, ? extends BaseCommandDto>> commandHandlers(
            CreateParcelCommand createParcelCommand,
            FindParcelByIdQuery findParcelByIdQuery,
            EditParcelCommand editParcelCommand,
            DeleteParcelCommand deleteParcelCommand,
            LoadParcelsCommand loadParcelsCommand,
            UnloadParcelsCommand unloadParcelsCommand,
            FindAllParcelsQuery findAllParcelsQuery) {
        Map<CommandType, Command<?, ?>> commandHandlers = new HashMap<>();
        commandHandlers.put(CommandType.CREATE, createParcelCommand);
        commandHandlers.put(CommandType.FIND, findParcelByIdQuery);
        commandHandlers.put(CommandType.EDIT, editParcelCommand);
        commandHandlers.put(CommandType.DELETE, deleteParcelCommand);
        commandHandlers.put(CommandType.LOAD, loadParcelsCommand);
        commandHandlers.put(CommandType.UNLOAD, unloadParcelsCommand);
        commandHandlers.put(CommandType.FINDALL, findAllParcelsQuery);
        return commandHandlers;
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
