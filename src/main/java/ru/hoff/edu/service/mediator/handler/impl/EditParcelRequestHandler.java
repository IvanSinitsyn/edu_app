package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.model.dto.response.ResponseDto;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.EditParcelRequest;

@Component
@RequiredArgsConstructor
public class EditParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public ResponseDto handle(Request request) {
        EditParcelRequest editRequest = (EditParcelRequest) request;
        Parcel parcel = parcelService.edit(
                editRequest.id(),
                editRequest.name(),
                parcelMapper.convertStringToForm(editRequest.form()),
                editRequest.symbol());

        return new EditParcelResponseDto(parcelMapper.toDto(parcel));
    }
}
