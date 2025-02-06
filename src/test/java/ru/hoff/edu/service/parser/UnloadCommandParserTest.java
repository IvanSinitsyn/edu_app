package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.parser.impl.UnloadCommandParser;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.UnloadParcelsRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class UnloadCommandParserTest {

    @Test
    void parseCommand_shouldReturnUnloadParcelsCommandDto() {
        CommandParser parser = new UnloadCommandParser();
        Request unloadDto = parser.parse("/unload --infile \"input.txt\" --outfile \"output.txt\" --withcount");
        assertThat(unloadDto).isInstanceOf(UnloadParcelsRequest.class);
    }

    @Test
    void parseUnloadCommand_shouldReturnUnloadParcelsCommandDto() {
        CommandParser parser = new UnloadCommandParser();

        String command = "/unload --infile \"input.txt\" --outfile \"output.txt\" --withcount";

        UnloadParcelsRequest dto = (UnloadParcelsRequest) parser.parse(command);

        assertThat(dto.inFileName()).isEqualTo("input.txt");
        assertThat(dto.outFileName()).isEqualTo("output.txt");
        assertThat(dto.withCount()).isTrue();
    }
}
