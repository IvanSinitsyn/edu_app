package ru.hoff.edu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "tg-bot-configuration")
public class TelegramBotProperties {

    private String botName;

    private String botToken;
}
