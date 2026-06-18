package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.DailyTrackingPersistenceEntity;

/**
 * Static assembler between daily tracking domain and persistence representations.
 */
public final class DailyTrackingPersistenceAssembler {

    private DailyTrackingPersistenceAssembler() {
    }

    public static DailyTracking toDomainFromPersistence(DailyTrackingPersistenceEntity entity) {
        if (entity == null) return null;
        var tracking = new DailyTracking();
        tracking.setId(entity.getId());
        tracking.setRoutineId(entity.getRoutineId());
        tracking.setUserId(entity.getUserId());
        tracking.setDate(entity.getDate());
        tracking.setStatus(entity.getStatus());
        return tracking;
    }

    public static DailyTrackingPersistenceEntity toPersistenceFromDomain(DailyTracking tracking) {
        if (tracking == null) return null;
        var entity = new DailyTrackingPersistenceEntity();
        if (tracking.getId() != null) {
            entity.setId(tracking.getId());
        }
        entity.setRoutineId(tracking.getRoutineId());
        entity.setUserId(tracking.getUserId());
        entity.setDate(tracking.getDate());
        entity.setStatus(tracking.getStatus());
        return entity;
    }
}