package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.EditParcelRequest;
import ru.hoff.edu.service.parser.impl.EditRequestParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EditRequestParserTest {

    @Test
    void parseCommand_shouldReturnEditParcelCommandDto() {
        RequestParser parser = new EditRequestParser();
        Request editDto = parser.parse("/edit --id \"1\" --name \"NewName\" --form \"Circle\" --symbol \"O\"");
        assertInstanceOf(EditParcelRequest.class, editDto);
    }

    @Test
    void parseEditParcelCommand_shouldReturnEditParcelCommandDto() {
        RequestParser parser = new EditRequestParser();

        String command = "/edit --id \"1\" --name \"NewName\" --form \"Circle\" --symbol \"O\"";

        EditParcelRequest dto = (EditParcelRequest) parser.parse(command);

        assertThat(dto.id()).isEqualTo("1");
        assertThat(dto.name()).isEqualTo("NewName");
        assertThat(dto.form()).isEqualTo("Circle");
        assertThat(dto.symbol()).isEqualTo("O");
    }
}
