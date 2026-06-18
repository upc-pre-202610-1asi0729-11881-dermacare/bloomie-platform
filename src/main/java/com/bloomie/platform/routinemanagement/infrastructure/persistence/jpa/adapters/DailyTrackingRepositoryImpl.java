package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.repositories.DailyTrackingRepository;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers.DailyTrackingPersistenceAssembler;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.repositories.DailyTrackingPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository adapter that bridges the daily tracking domain repository port with Spring Data JPA.
 */
@Repository
public class DailyTrackingRepositoryImpl implements DailyTrackingRepository {

    private final DailyTrackingPersistenceRepository dailyTrackingPersistenceRepository;

    public DailyTrackingRepositoryImpl(DailyTrackingPersistenceRepository dailyTrackingPersistenceRepository) {
        this.dailyTrackingPersistenceRepository = dailyTrackingPersistenceRepository;
    }

    @Override
    public List<DailyTracking> findAll() {
        return dailyTrackingPersistenceRepository.findAll().stream()
                .map(DailyTrackingPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}