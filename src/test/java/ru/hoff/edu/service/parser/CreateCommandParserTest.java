package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.parser.impl.CreateCommandParser;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.CreateParcelRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CreateCommandParserTest {

    @Test
    void parseCommand_shouldReturnCreateParcelCommandDto() {
        CommandParser parser = new CreateCommandParser();
        Request createDto = parser.parse("/create --name \"Test\" --form \"Rectangle\" --symbol \"X\"");
        assertInstanceOf(CreateParcelRequest.class, createDto);
    }

    @Test
    void parseCreateParcelCommand_shouldReturnCreateParcelCommandDto() {
        CommandParser parser = new CreateCommandParser();

        String command = "/create --name \"Parcel1\" --form \"Rectangle\" --symbol \"X\"";

        CreateParcelRequest dto = (CreateParcelRequest) parser.parse(command);

        assertThat(dto.name()).isEqualTo("Parcel1");
        assertThat(dto.form()).isEqualTo("Rectangle");
        assertThat(dto.symbol()).isEqualTo("X");
    }
}
