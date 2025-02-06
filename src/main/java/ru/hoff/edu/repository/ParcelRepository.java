package ru.hoff.edu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hoff.edu.model.entity.ParcelEntity;

/**
 * Класс-репозиторий для управления посылками (Parcel).
 * Предоставляет методы для добавления, редактирования, удаления и поиска посылок.
 */
public interface ParcelRepository extends JpaRepository<ParcelEntity, String> {
}
