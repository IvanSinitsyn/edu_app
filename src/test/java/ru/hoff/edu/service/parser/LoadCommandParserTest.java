package ru.hoff.edu.service.parser;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.util.filereader.FileReaderFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class LoadCommandParserTest {

    @Test
    void parseCommand_shouldReturnLoadParcelsCommandDto() {
        CommandParser parser = new LoadCommandParser(mock(FileReaderFactory.class));
        BaseCommandDto loadDto = parser.parse("/load -parcels-text \"Parcel1\\nParcel2\" -trucks \"Truck1\\nTruck2\" -algorithm \"easy\" -out file");
        assertInstanceOf(LoadParcelsCommandDto.class, loadDto);
    }

    @Test
    void parseLoadCommand_shouldReturnLoadParcelsCommandDto() {
        CommandParser parser = new LoadCommandParser(mock(FileReaderFactory.class));

        String command = "/load -parcels-text \"Parcel1\\nParcel2\" -trucks \"Truck1\\nTruck2\" -algorithm \"EASY\" -out file";

        LoadParcelsCommandDto dto = (LoadParcelsCommandDto) parser.parse(command);

        assertEquals(AlgorithmType.EASY, dto.getAlgorithmType());
        assertEquals(Arrays.asList("Parcel1", "Parcel2"), dto.getParcelIds());
        assertEquals(Arrays.asList("Truck1", "Truck2"), dto.getTrucksDescriptions());
        assertEquals(ResultOutType.FILE, dto.getResultOutType());
        assertNull(dto.getPathToResultFile());
    }
}
