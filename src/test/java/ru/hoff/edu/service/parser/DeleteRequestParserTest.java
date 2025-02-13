package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.DeleteParcelRequest;
import ru.hoff.edu.service.parser.impl.DeleteRequestParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class DeleteRequestParserTest {

    @Test
    void parseCommand_shouldReturnDeleteParcelCommandDto() {
        RequestParser parser = new DeleteRequestParser();
        Request deleteDto = parser.parse("/delete --name \"Parcel1\"");
        assertInstanceOf(DeleteParcelRequest.class, deleteDto);
    }

    @Test
    void parseDeleteParcelCommand_shouldReturnDeleteParcelCommandDto() {
        RequestParser parser = new DeleteRequestParser();

        String command = "/delete --name \"Parcel1\"";

        DeleteParcelRequest dto = (DeleteParcelRequest) parser.parse(command);

        assertThat(dto.parcelName()).isEqualTo("Parcel1");
    }
}
