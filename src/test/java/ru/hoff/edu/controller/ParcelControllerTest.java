package ru.hoff.edu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hoff.edu.model.dto.ParcelDto;
import ru.hoff.edu.model.dto.response.CreateParcelResponseDto;
import ru.hoff.edu.model.dto.response.ResponseDto;
import ru.hoff.edu.repository.ParcelRepository;
import ru.hoff.edu.service.mediator.Mediator;
import ru.hoff.edu.service.mediator.request.impl.CreateParcelRequest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class ParcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Mediator mediator;

    @Mock
    private ParcelRepository parcelRepository;

    @Test
    void createParcel_ShouldReturnCreated() throws Exception {
        CreateParcelRequest request = new CreateParcelRequest("Box", "BB", "B");
        ResponseDto responseDto = new CreateParcelResponseDto(new ParcelDto("Box", new char[][]{{'B', 'B'}}, "B", false));

        when(mediator.send(any(CreateParcelRequest.class))).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/parcels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.parcel.name").value("Box"))
                .andExpect(jsonPath("$.parcel.symbol").value("B"))
                .andExpect(jsonPath("$.parcel.isLoaded").value(false))
                .andExpect(jsonPath("$.parcel.form").isArray())
                .andExpect(jsonPath("$.parcel.form[0]").value("BB"));
    }
}
