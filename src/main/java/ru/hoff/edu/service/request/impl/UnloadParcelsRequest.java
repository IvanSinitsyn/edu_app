package ru.hoff.edu.service.request.impl;

import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.request.Request;

@Builder
public record UnloadParcelsRequest(String inFileName, String outFileName, boolean withCount) implements Request {

    public CommandType getCommandType() {
        return CommandType.UNLOAD;
    }
}
