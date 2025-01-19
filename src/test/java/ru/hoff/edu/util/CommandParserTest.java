package ru.hoff.edu.util;

import org.junit.jupiter.api.Test;
import ru.hoff.edu.dto.BaseCommandDto;
import ru.hoff.edu.dto.CreateParcelCommandDto;
import ru.hoff.edu.dto.DeleteParcelCommandDto;
import ru.hoff.edu.dto.EditParcelCommandDto;
import ru.hoff.edu.dto.FindParcelByIdQueryDto;
import ru.hoff.edu.dto.LoadParcelsCommandDto;
import ru.hoff.edu.dto.UnloadParcelsCommandDto;
import ru.hoff.edu.model.enums.AlgorithmType;
import ru.hoff.edu.model.enums.ResultOutType;
import ru.hoff.edu.util.filereader.FileReaderFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CommandParserTest {

    @Test
    void parseCommand_shouldReturnCorrectDtoBasedOnCommand() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        BaseCommandDto createDto = parser.parseCommand("/create -name \"Test\" -form \"Rectangle\" -symbol \"X\"");
        assertInstanceOf(CreateParcelCommandDto.class, createDto);

        BaseCommandDto deleteDto = parser.parseCommand("/delete \"Parcel1\"");
        assertInstanceOf(DeleteParcelCommandDto.class, deleteDto);

        BaseCommandDto editDto = parser.parseCommand("/edit -id \"1\" -name \"NewName\" -form \"Circle\" -symbol \"O\"");
        assertInstanceOf(EditParcelCommandDto.class, editDto);

        BaseCommandDto findDto = parser.parseCommand("/find \"Parcel2\"");
        assertInstanceOf(FindParcelByIdQueryDto.class, findDto);

        BaseCommandDto loadDto = parser.parseCommand("/load -parcels-text \"Parcel1\\nParcel2\" -trucks \"Truck1\\nTruck2\" -algorithm \"easy\" -out file");
        assertInstanceOf(LoadParcelsCommandDto.class, loadDto);

        BaseCommandDto unloadDto = parser.parseCommand("/unload -infile \"input.txt\" -outfile \"output.txt\" --withcount");
        assertInstanceOf(UnloadParcelsCommandDto.class, unloadDto);
    }

    @Test
    void parseLoadCommand_shouldReturnLoadParcelsCommandDto() {
        FileReaderFactory fileReaderFactory = mock(FileReaderFactory.class);
        CommandParser parser = new CommandParser(fileReaderFactory);

        String command = "/load -parcels-text \"Parcel1\\nParcel2\" -trucks \"Truck1\\nTruck2\" -algorithm \"EASY\" -out file";

        LoadParcelsCommandDto dto = (LoadParcelsCommandDto) parser.parseCommand(command);

        assertEquals(AlgorithmType.EASY, dto.getAlgorithmType());
        assertEquals(Arrays.asList("Parcel1", "Parcel2"), dto.getParcelIds());
        assertEquals(Arrays.asList("Truck1", "Truck2"), dto.getTrucksDescriptions());
        assertEquals(ResultOutType.FILE, dto.getResultOutType());
        assertNull(dto.getPathToResultFile());
    }

    @Test
    void parseUnloadCommand_shouldReturnUnloadParcelsCommandDto() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        String command = "/unload -infile \"input.txt\" -outfile \"output.txt\" --withcount";

        UnloadParcelsCommandDto dto = (UnloadParcelsCommandDto) parser.parseCommand(command);

        assertEquals("input.txt", dto.getInFileName());
        assertEquals("output.txt", dto.getOutFileName());
        assertTrue(dto.isWithCount());
    }

    @Test
    void parseCreateParcelCommand_shouldReturnCreateParcelCommandDto() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        String command = "/create -name \"Parcel1\" -form \"Rectangle\" -symbol \"X\"";

        CreateParcelCommandDto dto = (CreateParcelCommandDto) parser.parseCommand(command);

        assertEquals("Parcel1", dto.getName());
        assertEquals("Rectangle", dto.getForm());
        assertEquals("X", dto.getSymbol());
    }

    @Test
    void parseDeleteParcelCommand_shouldReturnDeleteParcelCommandDto() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        String command = "/delete \"Parcel1\"";

        DeleteParcelCommandDto dto = (DeleteParcelCommandDto) parser.parseCommand(command);

        assertEquals("Parcel1", dto.getParcelName());
    }

    @Test
    void parseEditParcelCommand_shouldReturnEditParcelCommandDto() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        String command = "/edit -id \"1\" -name \"NewName\" -form \"Circle\" -symbol \"O\"";

        EditParcelCommandDto dto = (EditParcelCommandDto) parser.parseCommand(command);

        assertEquals("1", dto.getId());
        assertEquals("NewName", dto.getName());
        assertEquals("Circle", dto.getForm());
        assertEquals("O", dto.getSymbol());
    }

    @Test
    void parseFindParcelByIdQuery_shouldReturnFindParcelByIdQueryDto() {
        CommandParser parser = new CommandParser(mock(FileReaderFactory.class));

        String command = "/find \"Parcel1\"";

        FindParcelByIdQueryDto dto = (FindParcelByIdQueryDto) parser.parseCommand(command);

        assertEquals("Parcel1", dto.getParcelName());
    }
}
