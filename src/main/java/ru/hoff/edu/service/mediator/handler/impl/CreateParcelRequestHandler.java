package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.model.dto.response.ResponseDto;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.CreateParcelRequest;

@Component
@RequiredArgsConstructor
public class CreateParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public ResponseDto handle(Request request) {
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
