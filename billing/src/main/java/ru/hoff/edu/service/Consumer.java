package ru.hoff.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.InboxEntity;
import ru.hoff.edu.model.enums.Status;
import ru.hoff.edu.repository.InboxRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class Consumer {

    private final InboxRepository inboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @KafkaListener(
            topics = "calculate-cheque-topic",
            groupId = "billing",
            containerFactory = "taskDtoConcurrentKafkaListenerContainerFactory")
    public void listen(ConsumerRecord<String, LoadCalculationDto> record) {
        try {
            InboxEntity inboxEntity = InboxEntity.builder()
                    .partition(record.partition())
                    .offset(record.offset())
                    .topic(record.topic())
                    .payload(convertToJson(record.value()))
                    .status(Status.NEW)
                    .build();

            inboxRepository.save(inboxEntity);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate message skipped: topic={}, partition={}, offset={}",
                    record.topic(), record.partition(), record.offset());
        }
    }

    private String convertToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON serialization error", e);
        }
    }
}
