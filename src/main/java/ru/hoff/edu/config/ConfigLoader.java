package ru.hoff.edu.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigLoader {

    public static TelegramBotConfig loadTelegramBotConfig() {
        Yaml yaml = new Yaml();
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application-local.yml")) {
            if (input == null) {
                throw new IllegalArgumentException("Configuration file not found");
            }
            Map<String, Object> configMap = yaml.load(input);

            Map<String, String> botConfigMap = (Map<String, String>) configMap.get("telegram-bot-configuration");
            if (botConfigMap == null) {
                throw new IllegalArgumentException("Missing 'telegram-bot-configuration' section in YAML");
            }

            TelegramBotConfig config = new TelegramBotConfig();
            config.setBotName(botConfigMap.get("bot-name"));
            config.setBotToken(botConfigMap.get("bot-token"));

            return config;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }
    }
}
