package ru.hoff.edu.service.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.service.ParcelService;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.DeleteParcelRequest;

@Component
@RequiredArgsConstructor
public class DeleteParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;

    @Override
    public Object handle(Request request) {
        DeleteParcelRequest deleteRequest = (DeleteParcelRequest) request;
        parcelService.delete(deleteRequest.parcelName());
        return new DeleteParcelResponseDto(String.format("Посылка %s удалена", deleteRequest.parcelName()));
    }
}
