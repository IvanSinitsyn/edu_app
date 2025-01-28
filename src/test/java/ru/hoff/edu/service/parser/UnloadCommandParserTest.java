package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.service.parser.impl.UnloadCommandParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnloadCommandParserTest {

    @Test
    void parseCommand_shouldReturnUnloadParcelsCommandDto() {
        CommandParser parser = new UnloadCommandParser();
        BaseCommandDto unloadDto = parser.parse("/unload --infile \"input.txt\" --outfile \"output.txt\" --withcount");
        assertInstanceOf(UnloadParcelsCommandDto.class, unloadDto);
    }

    @Test
    void parseUnloadCommand_shouldReturnUnloadParcelsCommandDto() {
        CommandParser parser = new UnloadCommandParser();

        String command = "/unload --infile \"input.txt\" --outfile \"output.txt\" --withcount";

        UnloadParcelsCommandDto dto = (UnloadParcelsCommandDto) parser.parse(command);

        assertEquals("input.txt", dto.inFileName());
        assertEquals("output.txt", dto.outFileName());
        assertTrue(dto.withCount());
    }
}
