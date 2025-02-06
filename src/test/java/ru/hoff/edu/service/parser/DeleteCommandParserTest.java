package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.parser.impl.DeleteCommandParser;
import ru.hoff.edu.service.request.Request;
import ru.hoff.edu.service.request.impl.DeleteParcelRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DeleteCommandParserTest {

    @Test
    void parseCommand_shouldReturnDeleteParcelCommandDto() {
        CommandParser parser = new DeleteCommandParser();
        Request deleteDto = parser.parse("/delete --name \"Parcel1\"");
        assertInstanceOf(DeleteParcelRequest.class, deleteDto);
    }

    @Test
    void parseDeleteParcelCommand_shouldReturnDeleteParcelCommandDto() {
        CommandParser parser = new DeleteCommandParser();

        String command = "/delete --name \"Parcel1\"";

        DeleteParcelRequest dto = (DeleteParcelRequest) parser.parse(command);

        assertThat(dto.parcelName()).isEqualTo("Parcel1");
    }
}
