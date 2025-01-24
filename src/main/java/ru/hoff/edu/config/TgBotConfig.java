package ru.hoff.edu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс конфигурации для Telegram-бота.
 * <p>
 * Этот класс используется для загрузки конфигурационных свойств Telegram-бота,
 * таких как имя бота и токен, из файла конфигурации (например, application.yml).
 * </p>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "tg-bot-configuration")
public class TgBotConfig {

    /**
     * Имя Telegram-бота.
     * <p>
     * Это имя используется для идентификации бота в Telegram.
     * </p>
     */
    private String botName;

    /**
     * Токен Telegram-бота.
     * <p>
     * Токен используется для аутентификации и взаимодействия с Telegram API.
     * </p>
     */
    private String botToken;
}
