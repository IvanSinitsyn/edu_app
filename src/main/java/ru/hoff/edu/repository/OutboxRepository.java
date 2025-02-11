package ru.hoff.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hoff.edu.model.entity.OutboxEntity;
import ru.hoff.edu.model.enums.MessageStatus;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxEntity, UUID> {

    List<OutboxEntity> findByStatus(MessageStatus status);
}
