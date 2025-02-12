package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.BaseResponseDto;
import ru.hoff.edu.model.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mapper.ParcelMapper;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.FindAllParcelsRequest;

@Component
@RequiredArgsConstructor
public class FindAllParcelsRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public BaseResponseDto handle(Request request) {
        FindAllParcelsRequest findAllRequest = (FindAllParcelsRequest) request;
        Page<Parcel> parcels = parcelService.findAll(findAllRequest.page(), findAllRequest.size());
        return new FindAllParcelsResponseDto(parcelMapper.toDtoList(parcels.getContent()), parcels.getTotalPages(), parcels.getTotalElements());
    }
}
