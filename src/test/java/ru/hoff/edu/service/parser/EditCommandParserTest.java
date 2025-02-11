package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.parser.impl.EditCommandParser;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.EditParcelRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class EditCommandParserTest {

    @Test
    void parseCommand_shouldReturnEditParcelCommandDto() {
        CommandParser parser = new EditCommandParser();
        Request editDto = parser.parse("/edit --id \"1\" --name \"NewName\" --form \"Circle\" --symbol \"O\"");
        assertInstanceOf(EditParcelRequest.class, editDto);
    }

    @Test
    void parseEditParcelCommand_shouldReturnEditParcelCommandDto() {
        CommandParser parser = new EditCommandParser();

        String command = "/edit --id \"1\" --name \"NewName\" --form \"Circle\" --symbol \"O\"";

        EditParcelRequest dto = (EditParcelRequest) parser.parse(command);

        assertThat(dto.id()).isEqualTo("1");
        assertThat(dto.name()).isEqualTo("NewName");
        assertThat(dto.form()).isEqualTo("Circle");
        assertThat(dto.symbol()).isEqualTo("O");
    }
}
