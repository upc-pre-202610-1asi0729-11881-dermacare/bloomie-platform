package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.DailyTrackingPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for daily tracking persistence entities.
 */
@Repository
public interface DailyTrackingPersistenceRepository extends JpaRepository<DailyTrackingPersistenceEntity, Long> {
}