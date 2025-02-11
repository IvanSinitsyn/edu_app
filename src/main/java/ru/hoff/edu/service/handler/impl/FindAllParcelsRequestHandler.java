package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.model.dto.response.FindAllParcelsResponseDto;
import ru.hoff.edu.service.ParcelMapper;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.FindAllParcelsRequest;

@Component
@RequiredArgsConstructor
public class FindAllParcelsRequestHandler implements RequestHandler {

    private final ParcelService parcelService;
    private final ParcelMapper parcelMapper;

    @Override
    public Object handle(Request request) {
        FindAllParcelsRequest findAllRequest = (FindAllParcelsRequest) request;
        Page<Parcel> parcels = parcelService.findAll(findAllRequest.page(), findAllRequest.size());
        return new FindAllParcelsResponseDto(parcelMapper.toDtoList(parcels.getContent()), parcels.getTotalPages(), parcels.getTotalElements());
    }
}
