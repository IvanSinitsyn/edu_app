package ru.hoff.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.service.BillingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cheques")
public class ChequeController {

    private final BillingService billingService;

    @GetMapping
    public ResponseEntity<List<Cheque>> findByClientId(
            @RequestParam String clientId,
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate) {
        List<Cheque> cheques = billingService.fetchChequesForClient(clientId, fromDate, toDate);
        return ResponseEntity.ok(cheques);
    }
}
