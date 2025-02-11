package ru.hoff.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hoff.edu.model.entity.ChequeEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс-репозиторий для управления чеками.
 * Предоставляет методы для добавления, редактирования, удаления и поиска чеков.
 */
public interface ChequeRepository extends JpaRepository<ChequeEntity, Long> {
    List<ChequeEntity> findAllByClientIdAndDateBetween(String clientId, LocalDate dateAfter, LocalDate dateBefore);
}
