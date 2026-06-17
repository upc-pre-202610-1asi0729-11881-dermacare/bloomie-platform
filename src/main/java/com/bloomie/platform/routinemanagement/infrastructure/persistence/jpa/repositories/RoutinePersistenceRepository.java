package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutinePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for routine persistence entities.
 */
@Repository
public interface RoutinePersistenceRepository extends JpaRepository<RoutinePersistenceEntity, Long> {
}