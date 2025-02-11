package ru.hoff.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

@EnableCaching
@SpringBootApplication
public class BillingApplication {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(BillingApplication.class, args);
    }
}