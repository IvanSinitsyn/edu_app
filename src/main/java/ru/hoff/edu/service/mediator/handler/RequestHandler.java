package ru.hoff.edu.service.mediator.handler;

import ru.hoff.edu.model.dto.response.BaseResponseDto;
import ru.hoff.edu.service.mediator.request.Request;

public interface RequestHandler {

    BaseResponseDto handle(Request request);
}
