package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.repository.ChequeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final ChequeRepository chequeRepository;
    private final ChequeMapper chequeMapper;

    @Cacheable(value = "billingCache", key = "#clientId")
    public List<Cheque> fetchChequesForClient(String clientId, LocalDate fromDate, LocalDate toDate) {
        return chequeMapper.toDtoList(chequeRepository.findAllByClientIdAndDateBetween(clientId, fromDate, toDate));
    }
}
