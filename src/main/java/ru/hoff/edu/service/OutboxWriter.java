package ru.hoff.edu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.OutboxEntity;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.model.enums.MessageStatus;
import ru.hoff.edu.repository.OutboxRepository;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OutboxWriter {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void write(String clientId, List<Truck> trucks, ChequeType chequeType) {
        List<Parcel> parcels = trucks.stream().flatMap(truck -> truck.getParcels().stream()).toList();

        long totalCountCells = parcels.stream()
                .flatMap(parcel -> Arrays.stream(parcel.getForm()))
                .flatMapToInt(row -> new String(row).chars())
                .filter(cell -> cell != ' ')
                .count();

        LoadCalculationDto loadCalculationDto = LoadCalculationDto.builder()
                .clientId(clientId)
                .cellsCount(totalCountCells)
                .chequeType(chequeType)
                .parcelsCount(parcels.size())
                .trucksCount(trucks.size())
                .build();

        outboxRepository.save(
                OutboxEntity.builder()
                        .payload(convertToJson(loadCalculationDto))
                        .status(MessageStatus.NEW)
                        .build());
    }

    private String convertToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON serialization error", e);
        }
    }
}
