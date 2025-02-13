package ru.hoff.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.InboxEntity;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.model.enums.Status;
import ru.hoff.edu.repository.ChequeRepository;
import ru.hoff.edu.repository.InboxRepository;
import ru.hoff.edu.service.mapper.ChequeMapper;
import ru.hoff.edu.service.strategy.ChequeCalculator;
import ru.hoff.edu.util.HashUtil;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxProcessor {

    private final InboxRepository inboxRepository;
    private final ChequeRepository chequeRepository;
    private final Map<ChequeType, ChequeCalculator> chequeCalculators;
    private final ObjectMapper objectMapper;
    private final ChequeMapper chequeMapper;
    private final HashUtil hashUtil;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processInboxMessages() {
        List<InboxEntity> messages = inboxRepository.findByStatus(Status.NEW);

        for (InboxEntity message : messages) {
            try {
                log.info("Processing message: {}", message.getHash());
                LoadCalculationDto loadCalculationDto = objectMapper.readValue(message.getPayload(), LoadCalculationDto.class);
                Cheque cheque = chequeCalculators.get(loadCalculationDto.chequeType()).calculate(
                        loadCalculationDto.cellsCount(),
                        loadCalculationDto.trucksCount(),
                        loadCalculationDto.parcelsCount());

                cheque.setClientId(loadCalculationDto.clientId());
                chequeRepository.save(chequeMapper.toEntity(cheque));

                message.setStatus(Status.PROCESSED);
                inboxRepository.save(message);
            } catch (Exception ex) {
                log.error("Failed to process message {}: {}", message.getHash(), ex.getMessage());
                message.setStatus(Status.FAILED);
                inboxRepository.save(message);
            }
        }
    }

    public void saveInboxMessage(LoadCalculationDto loadCalculationDto) {
        try {
            InboxEntity inboxEntity = InboxEntity.builder()
                    .hash(hashUtil.generateHash(loadCalculationDto))
                    .payload(hashUtil.serialize(loadCalculationDto))
                    .status(Status.NEW)
                    .build();

            inboxRepository.save(inboxEntity);
        } catch (DataIntegrityViolationException ex) {
            log.warn("Duplicate message skipped: message={}", loadCalculationDto);
        } catch (JsonProcessingException e) {
            log.error("Error while serialize: message={}", loadCalculationDto, e);
        }
    }
}
