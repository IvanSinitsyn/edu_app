package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.CreateParcelRequest;
import ru.hoff.edu.service.parser.impl.CreateRequestParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CreateRequestParserTest {

    @Test
    void parseCommand_shouldReturnCreateParcelCommandDto() {
        RequestParser parser = new CreateRequestParser();
        Request createDto = parser.parse("/create --name \"Test\" --form \"Rectangle\" --symbol \"X\"");
        assertInstanceOf(CreateParcelRequest.class, createDto);
    }

    @Test
    void parseCreateParcelCommand_shouldReturnCreateParcelCommandDto() {
        RequestParser parser = new CreateRequestParser();

        String command = "/create --name \"Parcel1\" --form \"Rectangle\" --symbol \"X\"";

        CreateParcelRequest dto = (CreateParcelRequest) parser.parse(command);

        assertThat(dto.name()).isEqualTo("Parcel1");
        assertThat(dto.form()).isEqualTo("Rectangle");
        assertThat(dto.symbol()).isEqualTo("X");
    }
}
