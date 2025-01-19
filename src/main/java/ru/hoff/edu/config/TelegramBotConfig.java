package ru.hoff.edu.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class TelegramBotConfig {

    @Value("${telegram-bot-configuration.bot-name}")
    private String botName;

    @Value("${telegram-bot-configuration.bot-token}")
    private String botToken;
}
