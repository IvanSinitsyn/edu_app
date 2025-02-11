package ru.hoff.edu.controller;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.service.BillingService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
public class ChequeControllerTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BillingService billingService;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    public void getCheques_ShouldReturnCheques() throws Exception {
        List<Cheque> mockCheques = List.of(
                Cheque.builder().date(LocalDate.of(2024, 1, 10)).clientId("client123").trucksCount(1).parcelCount(1).cost(new BigDecimal(100)).build(),
                Cheque.builder().date(LocalDate.of(2024, 1, 11)).clientId("client123").trucksCount(1).parcelCount(2).cost(new BigDecimal(150)).build()
        );

        Mockito.when(billingService.fetchChequesForClient(
                Mockito.eq("client123"),
                Mockito.any(LocalDate.class),
                Mockito.any(LocalDate.class))
        ).thenReturn(mockCheques);

        mockMvc.perform(MockMvcRequestBuilders.get("/billing/client123")
                        .param("fromDate", "2024-01-01")
                        .param("toDate", "2024-01-31")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].parcelCount").value(1))
                .andExpect(jsonPath("$[1].parcelCount").value(2));
    }
}
