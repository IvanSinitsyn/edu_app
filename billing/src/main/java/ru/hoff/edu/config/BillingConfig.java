package ru.hoff.edu.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BillingConfig {

    @Bean
    public ObjectMapper objectWriter() {
        return new ObjectMapper();
    }
}
