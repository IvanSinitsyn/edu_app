package ru.hoff.edu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.service.InboxMessageStreamService;

import java.util.function.Consumer;

@Configuration
public class ConsumerConfig {

    @Bean
    public Consumer<Message<LoadCalculationDto>> handleLoadCalculation(InboxMessageStreamService inboxMessageStreamService) {
        return message -> inboxMessageStreamService.handle(message.getPayload());
    }
}
