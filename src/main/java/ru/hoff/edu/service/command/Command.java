package ru.hoff.edu.service.command;

import ru.hoff.edu.dto.BaseCommandDto;

public interface Command<T, D extends BaseCommandDto> {

    T execute(D commandDto);
}
