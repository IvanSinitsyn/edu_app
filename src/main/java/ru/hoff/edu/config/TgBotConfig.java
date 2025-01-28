package ru.hoff.edu.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Класс конфигурации для Telegram-бота.
 * <p>
 * Этот класс используется для загрузки конфигурационных свойств Telegram-бота,
 * таких как имя бота и токен, из файла конфигурации (например, application.yml).
 * </p>
 */
@ConfigurationProperties(prefix = "tg-bot-configuration")
public record TgBotConfig(String botName, String botToken) {
}
