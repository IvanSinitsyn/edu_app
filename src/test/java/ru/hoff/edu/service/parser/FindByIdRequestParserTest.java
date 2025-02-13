package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.FindParcelByIdRequest;
import ru.hoff.edu.service.parser.impl.FindByIdRequestParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FindByIdRequestParserTest {

    @Test
    void parseCommand_shouldReturnFindParcelByIdQueryDto() {
        RequestParser parser = new FindByIdRequestParser();
        Request findDto = parser.parse("/find --name \"Parcel2\"");
        assertInstanceOf(FindParcelByIdRequest.class, findDto);
    }

    @Test
    void parseFindParcelByIdQuery_shouldReturnFindParcelByIdQueryDto() {
        RequestParser parser = new FindByIdRequestParser();

        String command = "/find --name \"Parcel1\"";

        FindParcelByIdRequest dto = (FindParcelByIdRequest) parser.parse(command);

        assertThat(dto.parcelName()).isEqualTo("Parcel1");
    }
}
