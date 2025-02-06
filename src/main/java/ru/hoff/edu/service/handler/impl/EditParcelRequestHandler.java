package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.EditParcelResponseDto;
import ru.hoff.edu.service.DataConverter;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.EditParcelRequest;

@Component
@RequiredArgsConstructor
public class EditParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;
    private final DataConverter dataConverter;

    @Override
    public Object handle(Request request) {
        EditParcelRequest editRequest = (EditParcelRequest) request;
        Parcel parcel = parcelService.edit(
                editRequest.id(),
                editRequest.name(),
                dataConverter.convertStringToForm(editRequest.form()),
                editRequest.symbol());

        return new EditParcelResponseDto(parcelMapper.toDto(parcel));
    }
}
