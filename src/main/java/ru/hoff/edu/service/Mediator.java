package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hoff.edu.service.handler.RequestHandler;
import ru.hoff.edu.service.request.Request;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Mediator {

    private final Map<Class<? extends Request>, RequestHandler> handlers;

    public Object send(Request request) {
        RequestHandler handler = handlers.get(request.getClass());
        if (handler == null) {
            throw new IllegalStateException("No handler found for: " + request.getClass().getSimpleName());
        }
        return handler.handle(request);
    }
}
