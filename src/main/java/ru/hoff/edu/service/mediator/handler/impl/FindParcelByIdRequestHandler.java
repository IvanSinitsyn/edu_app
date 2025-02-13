package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.model.dto.response.ResponseDto;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.FindParcelByIdRequest;

@Component
@RequiredArgsConstructor
public class FindParcelByIdRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public ResponseDto handle(Request request) {
        FindParcelByIdRequest findByIdRequest = (FindParcelByIdRequest) request;
        Parcel parcel = parcelService.findByName(findByIdRequest.parcelName());
        return new FindParcelByIdResponseDto(parcelMapper.toDto(parcel));
    }
}
