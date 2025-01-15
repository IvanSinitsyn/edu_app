package ru.hoff.edu.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "telegram-bot-configuration")
@Component
@Data
public class TelegramBotConfig {

    private String botName;

    private String botToken;
}
