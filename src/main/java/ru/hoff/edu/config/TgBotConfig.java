package ru.hoff.edu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "tg-bot-configuration")
public class TgBotConfig {

    private String botName;
    private String botToken;
}
