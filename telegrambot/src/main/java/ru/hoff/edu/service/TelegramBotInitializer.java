package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import ru.hoff.edu.controller.TelegramController;

/**
 * Класс TelegramBotInitializer отвечает за инициализацию и регистрацию Telegram бота
 * при запуске Spring Boot приложения. Реализует интерфейс {@link ApplicationRunner},
 * что позволяет выполнить код после полного запуска контекста Spring.
 *
 * <p>Класс использует {@link TelegramBotsApi} для регистрации бота и {@link TelegramController}
 * для обработки входящих сообщений и команд.</p>
 *
 * <p>При успешной регистрации бота в системе логируется соответствующее сообщение.</p>
 *
 * @see ApplicationRunner
 * @see TelegramBotsApi
 * @see TelegramController
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TelegramBotInitializer implements ApplicationRunner {

    private final TelegramBotsApi botsApi;
    private final TelegramController telegramController;

    /**
     * Метод, вызываемый после запуска Spring Boot приложения. Регистрирует бота
     * с использованием {@link TelegramBotsApi} и логирует успешную регистрацию.
     *
     * @param args Аргументы, переданные при запуске приложения.
     * @throws Exception Если произошла ошибка при регистрации бота.
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        botsApi.registerBot(telegramController);
        log.info("Telegram bot registered successfully.");
    }
}
