package ru.hoff.edu.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.OutboxEntity;
import ru.hoff.edu.model.enums.MessageStatus;
import ru.hoff.edu.repository.OutboxRepository;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxProcessor {

    private final OutboxRepository outboxRepository;
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        List<OutboxEntity> outboxEntities = outboxRepository.findByStatus(MessageStatus.NEW);

        for (OutboxEntity outboxEntity : outboxEntities) {
            try {
                LoadCalculationDto payload = objectMapper.readValue(outboxEntity.getPayload(), LoadCalculationDto.class);
                Message<LoadCalculationDto> message = MessageBuilder
                        .withPayload(payload)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .build();

                streamBridge.send("calculate-cheque-topic", message);

                outboxEntity.setStatus(MessageStatus.SENT);
                outboxRepository.save(outboxEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON deserialization error", e);
            } catch (Exception ex) {
                log.error("Failed to send message {}: {}", outboxEntity.getHash(), ex.getMessage());
            }
        }
    }
}
