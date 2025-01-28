package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.service.parser.impl.FindByIdCommandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FindByIdCommandParserTest {

    @Test
    void parseCommand_shouldReturnFindParcelByIdQueryDto() {
        CommandParser parser = new FindByIdCommandParser();
        BaseCommandDto findDto = parser.parse("/find --name \"Parcel2\"");
        assertInstanceOf(FindParcelByIdQueryDto.class, findDto);
    }

    @Test
    void parseFindParcelByIdQuery_shouldReturnFindParcelByIdQueryDto() {
        CommandParser parser = new FindByIdCommandParser();

        String command = "/find --name \"Parcel1\"";

        FindParcelByIdQueryDto dto = (FindParcelByIdQueryDto) parser.parse(command);

        assertEquals("Parcel1", dto.parcelName());
    }
}
