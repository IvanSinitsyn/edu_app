package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.model.TruckData;
import ru.hoff.edu.model.dto.response.BaseResponseDto;
import ru.hoff.edu.model.dto.response.UnloadParcelsResponseDto;
import ru.hoff.edu.model.enums.ChequeType;
import ru.hoff.edu.service.core.ParcelCounter;
import ru.hoff.edu.service.filewriter.ParcelFileWriter;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.UnloadParcelsRequestDto;
import ru.hoff.edu.service.outbox.OutboxWriter;
import ru.hoff.edu.service.parser.TruckDataParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UnloadParcelsRequestHandler implements RequestHandler {

    private final TruckDataParser truckDataParser;
    private final ParcelCounter parcelCounter;
    private final ParcelFileWriter parcelFileWriter;
    private final OutboxWriter outboxWriter;

    @Override
    public BaseResponseDto handle(Request request) {
        UnloadParcelsRequestDto requestDto = (UnloadParcelsRequestDto) request;
        List<TruckData> trucksData = truckDataParser.parse(requestDto.inFileName());
        List<String> allParcelNames = trucksData.stream()
                .flatMap(truck -> truck.getParcels().stream())
                .map(Parcel::getName)
                .toList();

        List<Truck> trucks = trucksData.stream().map(TruckData::getTruck).toList();

        List<String> result;
        if (requestDto.withCount()) {
            result = parcelCounter.countParcels(allParcelNames);
        } else {
            result = new ArrayList<>(new HashSet<>(allParcelNames));
        }

        parcelFileWriter.write(requestDto.outFileName(), result);

        outboxWriter.write(requestDto.userId(), trucks, ChequeType.UNLOAD);

        return new UnloadParcelsResponseDto("Погрузка выполнена");
    }
}
