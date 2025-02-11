package ru.hoff.edu.service.handler;

import ru.hoff.edu.service.request.Request;

public interface RequestHandler {

    Object handle(Request request);
}
