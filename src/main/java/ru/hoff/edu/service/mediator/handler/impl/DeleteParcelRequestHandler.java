package ru.hoff.edu.service.mediator.handler.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.model.dto.response.BaseResponseDto;
import ru.hoff.edu.model.dto.response.DeleteParcelResponseDto;
import ru.hoff.edu.service.core.ParcelService;
import ru.hoff.edu.service.mediator.handler.RequestHandler;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.DeleteParcelRequest;

@Component
@RequiredArgsConstructor
public class DeleteParcelRequestHandler implements RequestHandler {

    private final ParcelService parcelService;

    @Override
    public BaseResponseDto handle(Request request) {
        DeleteParcelRequest deleteRequest = (DeleteParcelRequest) request;
        parcelService.delete(deleteRequest.parcelName());
        return new DeleteParcelResponseDto(String.format("Посылка %s удалена", deleteRequest.parcelName()));
    }
}
