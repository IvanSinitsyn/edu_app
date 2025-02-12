package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hoff.edu.model.dto.LoadCalculationDto;

@Service
@RequiredArgsConstructor
public class InboxMessageStreamService {

    private final InboxProcessor inboxProcessor;

    public void handle(LoadCalculationDto loadCalculationDto) {
        inboxProcessor.saveInboxMessage(loadCalculationDto);
    }
}
