package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.service.factory.FileReaderFactory;
import ru.hoff.edu.service.mediator.request.Request;
import ru.hoff.edu.service.mediator.request.impl.LoadParcelsRequest;
import ru.hoff.edu.service.parser.impl.LoadRequestParser;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class LoadRequestParserTest {

    @Test
    void parseCommand_shouldReturnLoadParcelsCommandDto() {
        RequestParser parser = new LoadRequestParser(mock(FileReaderFactory.class), mock(FileExtensionParser.class));
        Request loadDto = parser.parse("/load --user-id \"Ivan\" --parcels-text \"Parcel1\\nParcel2\" --trucks \"Truck1\\nTruck2\" --algorithm \"easy\" --out file");
        assertThat(loadDto).isInstanceOf(LoadParcelsRequest.class);
    }

    @Test
    void parseLoadCommand_shouldReturnLoadParcelsCommandDto() {
        RequestParser parser = new LoadRequestParser(mock(FileReaderFactory.class), mock(FileExtensionParser.class));

        String command = "/load --user-id \"Ivan\" --parcels-text \"Parcel1\\nParcel2\" --trucks \"Truck1\\nTruck2\" --algorithm \"EASY\" --out file";

        LoadParcelsRequest dto = (LoadParcelsRequest) parser.parse(command);

        assertThat(dto.algorithmType()).isEqualTo(AlgorithmType.EASY);
        assertThat(dto.parcelIds()).isEqualTo(Arrays.asList("Parcel1", "Parcel2"));
        assertThat(dto.trucksDescriptions()).isEqualTo(Arrays.asList("Truck1", "Truck2"));
        assertThat(dto.resultOutType()).isEqualTo(ResultOutType.FILE);
        assertThat(dto.pathToResultFile()).isNull();
    }
}
