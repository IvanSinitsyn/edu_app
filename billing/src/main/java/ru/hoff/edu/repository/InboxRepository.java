package ru.hoff.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hoff.edu.model.entity.InboxEntity;
import ru.hoff.edu.model.enums.Status;

import java.util.List;
import java.util.UUID;

public interface InboxRepository extends JpaRepository<InboxEntity, UUID> {

    List<InboxEntity> findByStatus(Status status);
}
