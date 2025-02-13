package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.UnloadParcelsRequestDto;
import ru.hoff.edu.service.parser.impl.UnloadRequestParser;

import static org.assertj.core.api.Assertions.assertThat;

public class UnloadRequestParserTest {

    @Test
    void parseCommand_shouldReturnUnloadParcelsCommandDto() {
        RequestParser parser = new UnloadRequestParser();
        Request unloadDto = parser.parse("/unload --user-id \"(.*?)\" --infile \"input.txt\" --outfile \"output.txt\" --withcount");
        assertThat(unloadDto).isInstanceOf(UnloadParcelsRequestDto.class);
    }

    @Test
    void parseUnloadCommand_shouldReturnUnloadParcelsCommandDto() {
        RequestParser parser = new UnloadRequestParser();

        String command = "/unload --user-id \"(.*?)\" --infile \"input.txt\" --outfile \"output.txt\" --withcount";

        UnloadParcelsRequestDto dto = (UnloadParcelsRequestDto) parser.parse(command);

        assertThat(dto.inFileName()).isEqualTo("input.txt");
        assertThat(dto.outFileName()).isEqualTo("output.txt");
        assertThat(dto.withCount()).isTrue();
    }
}
