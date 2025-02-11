package ru.hoff.edu.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.InboxEntity;
import ru.hoff.edu.model.enums.Status;
import ru.hoff.edu.repository.ChequeRepository;
import ru.hoff.edu.repository.InboxRepository;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboxProcessor {

    private final InboxRepository inboxRepository;
    private final ChequeRepository chequeRepository;
    private final ChequeCalculator chequeCalculator;
    private final ObjectMapper objectMapper;
    private final ChequeMapper chequeMapper;

    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void processInboxMessages() {
        List<InboxEntity> messages = inboxRepository.findByStatus(Status.NEW);

        for (InboxEntity message : messages) {
            try {
                log.info("Processing message: {}", message.getId());
                LoadCalculationDto loadCalculationDto = objectMapper.readValue(message.getPayload(), LoadCalculationDto.class);
                Cheque cheque = chequeCalculator.calculate(
                        loadCalculationDto.cellsCount(),
                        loadCalculationDto.trucksCount(),
                        loadCalculationDto.parcelsCount(),
                        loadCalculationDto.chequeType());

                cheque.setClientId(loadCalculationDto.clientId());
                chequeRepository.save(chequeMapper.toEntity(cheque));

                message.setStatus(Status.PROCESSED);
                inboxRepository.save(message);
            } catch (Exception ex) {
                log.error("Failed to process message {}: {}", message.getId(), ex.getMessage());
                message.setStatus(Status.FAILED);
                inboxRepository.save(message);
            }
        }
    }
}
