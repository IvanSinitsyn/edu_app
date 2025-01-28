package ru.hoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@ConfigurationPropertiesScan
@SpringBootApplication
public class EduAppApplication {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(EduAppApplication.class, args);
    }
}