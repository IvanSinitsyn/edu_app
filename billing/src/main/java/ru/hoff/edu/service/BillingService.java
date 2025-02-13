package ru.hoff.edu.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.hoff.edu.model.Cheque;
import ru.hoff.edu.repository.ChequeRepository;
import ru.hoff.edu.service.mapper.ChequeMapper;

import java.time.LocalDate;
import java.util.List;

@Slf4j
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
