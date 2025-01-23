package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EditCommandParserTest {

    @Test
    void parseCommand_shouldReturnEditParcelCommandDto() {
        CommandParser parser = new EditCommandParser();
        BaseCommandDto editDto = parser.parse("/edit -id \"1\" -name \"NewName\" -form \"Circle\" -symbol \"O\"");
        assertInstanceOf(EditParcelCommandDto.class, editDto);
    }

    @Test
    void parseEditParcelCommand_shouldReturnEditParcelCommandDto() {
        CommandParser parser = new EditCommandParser();

        String command = "/edit -id \"1\" -name \"NewName\" -form \"Circle\" -symbol \"O\"";

        EditParcelCommandDto dto = (EditParcelCommandDto) parser.parse(command);

        assertEquals("1", dto.getId());
        assertEquals("NewName", dto.getName());
        assertEquals("Circle", dto.getForm());
        assertEquals("O", dto.getSymbol());
    }
}
