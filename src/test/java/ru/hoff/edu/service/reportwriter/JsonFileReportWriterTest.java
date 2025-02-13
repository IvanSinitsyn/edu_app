package ru.hoff.edu.service.reportwriter;

import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.hoff.edu.domain.Parcel;
import ru.hoff.edu.domain.Truck;
import ru.hoff.edu.service.exception.JsonFileWriterException;
import ru.hoff.edu.service.mapper.ParcelMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JsonFileReportWriterTest {

    @TempDir
    Path tempDir;
    @Mock
    private ObjectWriter objectWriter;
    @Mock
    private ParcelMapper parcelMapper;
    private JsonFileReportWriter jsonFileReportWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jsonFileReportWriter = new JsonFileReportWriter(objectWriter, parcelMapper);
    }

    @Test
    void write_ShouldSuccessfullyWriteToJsonFile() throws IOException {
        Truck truck = mock(Truck.class);
        Parcel parcel = mock(Parcel.class);

        when(truck.getParcels()).thenReturn(List.of(parcel));
        when(parcel.getName()).thenReturn("Parcel1");
        when(parcelMapper.convertArrayToString(any())).thenReturn("Shape1");

        List<Truck> trucks = List.of(truck);
        String outputPath = tempDir.resolve("report.json").toString();

        doNothing().when(objectWriter).writeValue(any(OutputStream.class), any(List.class));

        String result = jsonFileReportWriter.write(trucks, outputPath);

        assertThat(result).isEqualTo("Report saved to " + outputPath);
        assertThat(Files.exists(Paths.get(outputPath))).isTrue();
        verify(objectWriter, times(1)).writeValue(any(OutputStream.class), any(List.class));
    }

    @Test
    void write_ShouldThrowException_WhenIOExceptionOccurs() throws IOException {
        Truck truck = mock(Truck.class);
        List<Truck> trucks = List.of(truck);
        String outputPath = tempDir.resolve("report.json").toString();

        doThrow(new IOException("File write error")).when(objectWriter)
                .writeValue(any(OutputStream.class), any(List.class));

        assertThatThrownBy(() -> jsonFileReportWriter.write(trucks, outputPath))
                .isInstanceOf(JsonFileWriterException.class)
                .hasMessageContaining("Error while writing to: " + outputPath);
    }

    @Test
    void write_ShouldThrowException_WhenFileCreationFails() {
        String outputPath = "/invalid_path/report.json";

        assertThatThrownBy(() -> jsonFileReportWriter.write(List.of(), outputPath))
                .isInstanceOf(JsonFileWriterException.class)
                .hasMessageContaining("Error while writing to: " + outputPath);
    }
}
