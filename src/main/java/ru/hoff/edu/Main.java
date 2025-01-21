package ru.hoff.edu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.hoff.edu.config.TgBotConfig;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@EnableConfigurationProperties(TgBotConfig.class)
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(Main.class, args);
    }
}