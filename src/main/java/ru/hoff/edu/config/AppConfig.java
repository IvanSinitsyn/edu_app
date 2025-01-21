package ru.hoff.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.service.command.Command;
import ru.hoff.edu.service.command.handler.CreateParcelCommandHandler;
import ru.hoff.edu.service.command.handler.DeleteParcelCommandHandler;
import ru.hoff.edu.service.command.handler.EditParcelCommandHandler;
import ru.hoff.edu.service.command.handler.FindParcelByIdQueryHandler;
import ru.hoff.edu.service.command.handler.LoadParcelsCommandHandler;
import ru.hoff.edu.service.command.handler.UnloadParcelsCommandHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AppConfig {

    @Bean
    public Map<String, Command<?, ? extends BaseCommandDto>> commandHandlers(
            CreateParcelCommandHandler createParcelCommandHandler,
            FindParcelByIdQueryHandler findParcelByIdQueryHandler,
            EditParcelCommandHandler editParcelCommandHandler,
            DeleteParcelCommandHandler deleteParcelCommandHandler,
            LoadParcelsCommandHandler loadParcelsCommandHandler,
            UnloadParcelsCommandHandler unloadParcelsCommandHandler) {
        Map<String, Command<?, ?>> commandHandlers = new HashMap<>();
        commandHandlers.put("/create", createParcelCommandHandler);
        commandHandlers.put("/find", findParcelByIdQueryHandler);
        commandHandlers.put("/edit", editParcelCommandHandler);
        commandHandlers.put("/delete", deleteParcelCommandHandler);
        commandHandlers.put("/load", loadParcelsCommandHandler);
        commandHandlers.put("/unload", unloadParcelsCommandHandler);
        return commandHandlers;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }
}
