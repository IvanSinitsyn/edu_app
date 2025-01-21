package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DeleteCommandParserTest {

    @Test
    void parseCommand_shouldReturnDeleteParcelCommandDto() {
        CommandParser parser = new DeleteCommandParser();
        BaseCommandDto deleteDto = parser.parse("/delete \"Parcel1\"");
        assertInstanceOf(DeleteParcelCommandDto.class, deleteDto);
    }

    @Test
    void parseDeleteParcelCommand_shouldReturnDeleteParcelCommandDto() {
        CommandParser parser = new DeleteCommandParser();

        String command = "/delete \"Parcel1\"";

        DeleteParcelCommandDto dto = (DeleteParcelCommandDto) parser.parse(command);

        assertEquals("Parcel1", dto.getParcelName());
    }
}
