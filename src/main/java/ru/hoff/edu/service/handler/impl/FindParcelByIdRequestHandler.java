package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.FindParcelByIdResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.FindParcelByIdRequest;

@Component
@RequiredArgsConstructor
public class FindParcelByIdRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public Object handle(Request request) {
        FindParcelByIdRequest findByIdRequest = (FindParcelByIdRequest) request;
        Parcel parcel = parcelService.findByName(findByIdRequest.parcelName());
        return new FindParcelByIdResponseDto(parcelMapper.toDto(parcel));
    }
}
