package ru.hoff.edu.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.dto.LoadCalculationDto;
import ru.hoff.edu.model.entity.OutboxEntity;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.model.enums.MessageStatus;
import ru.hoff.edu.repository.OutboxRepository;
import ru.hoff.edu.service.util.HashUtil;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxWriter {

    private final OutboxRepository outboxRepository;
    private final HashUtil hashUtil;

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

        try {
            outboxRepository.save(
                    OutboxEntity.builder()
                            .hash(hashUtil.generateHash(loadCalculationDto))
                            .payload(hashUtil.serialize(loadCalculationDto))
                            .status(MessageStatus.NEW)
                            .build());
        } catch (JsonProcessingException e) {
            log.error("Error while serialize: message={}", loadCalculationDto, e);
        }
    }
}
