package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutineItemPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for routine item persistence entities.
 */
@Repository
public interface RoutineItemPersistenceRepository extends JpaRepository<RoutineItemPersistenceEntity, Long> {
}