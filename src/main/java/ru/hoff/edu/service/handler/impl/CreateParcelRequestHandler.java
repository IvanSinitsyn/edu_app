package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.CreateParcelRequest;

@Component
@RequiredArgsConstructor
public class CreateParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public Object handle(Request request) {
        CreateParcelRequest createRequest = (CreateParcelRequest) request;
        Parcel parcel = Parcel.builder()
                .name(createRequest.name())
                .form(parcelMapper.convertStringToForm(createRequest.form()))
                .symbol(createRequest.symbol())
                .build();
        parcelService.add(parcel);
        return new CreateParcelResponseDto(parcelMapper.toDto(parcel));
    }
}
