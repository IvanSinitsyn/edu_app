package ru.hoff.edu.service.request.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import ru.hoff.edu.model.enums.CommandType;
import ru.hoff.edu.service.request.Request;

@Builder
public record EditParcelRequest(String id, String name, String form, String symbol) implements Request {

    @JsonIgnore
    public CommandType getCommandType() {
        return CommandType.EDIT;
    }
}
