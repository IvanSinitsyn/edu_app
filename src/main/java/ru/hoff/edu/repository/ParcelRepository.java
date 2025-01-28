package ru.hoff.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hoff.edu.entity.ParcelEntity;

/**
 * Класс-репозиторий для управления посылками (Parcel).
 * Предоставляет методы для добавления, редактирования, удаления и поиска посылок.
 */
@Repository
public interface ParcelRepository extends JpaRepository<ParcelEntity, String> {
}
