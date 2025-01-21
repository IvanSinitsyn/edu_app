package ru.hoff.edu.service.parser;

import ru.hoff.edu.dto.BaseCommandDto;

public interface CommandParser {

    BaseCommandDto parse(String command);
}
