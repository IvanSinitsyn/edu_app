package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.CreateParcelCommandDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CreateCommandParserTest {

    @Test
    void parseCommand_shouldReturnCreateParcelCommandDto() {
        CommandParser parser = new CreateCommandParser();
        BaseCommandDto createDto = parser.parse("/create -name \"Test\" -form \"Rectangle\" -symbol \"X\"");
        assertInstanceOf(CreateParcelCommandDto.class, createDto);
    }

    @Test
    void parseCreateParcelCommand_shouldReturnCreateParcelCommandDto() {
        CommandParser parser = new CreateCommandParser();

        String command = "/create -name \"Parcel1\" -form \"Rectangle\" -symbol \"X\"";

        CreateParcelCommandDto dto = (CreateParcelCommandDto) parser.parse(command);

        assertEquals("Parcel1", dto.getName());
        assertEquals("Rectangle", dto.getForm());
        assertEquals("X", dto.getSymbol());
    }
}
