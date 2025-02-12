package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.parser.impl.FindByIdCommandParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FindByIdCommandParserTest {

    @Test
    void parseCommand_shouldReturnFindParcelByIdQueryDto() {
        CommandParser parser = new FindByIdCommandParser();
        Request findDto = parser.parse("/find --name \"Parcel2\"");
        assertInstanceOf(FindParcelByIdRequest.class, findDto);
    }

    @Test
    void parseFindParcelByIdQuery_shouldReturnFindParcelByIdQueryDto() {
        CommandParser parser = new FindByIdCommandParser();

        String command = "/find --name \"Parcel1\"";

        FindParcelByIdRequest dto = (FindParcelByIdRequest) parser.parse(command);

        assertThat(dto.parcelName()).isEqualTo("Parcel1");
    }
}
