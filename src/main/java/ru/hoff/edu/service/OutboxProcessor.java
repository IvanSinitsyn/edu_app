package ru.hoff.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, LoadCalculationDto> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processOutbox() {
        List<OutboxEntity> outboxEntities = outboxRepository.findByStatus(MessageStatus.NEW);

        for (OutboxEntity outboxEntity : outboxEntities) {
            try {
                kafkaTemplate.send(
                        "calculate-cheque-topic",
                        objectMapper.readValue(outboxEntity.getPayload(), LoadCalculationDto.class));

                outboxEntity.setStatus(MessageStatus.SENT);
                outboxRepository.save(outboxEntity);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON deserialization error", e);
            } catch (Exception ex) {
                log.error("Failed to send message {}: {}", outboxEntity.getId(), ex.getMessage());
            }
        }
    }
}
