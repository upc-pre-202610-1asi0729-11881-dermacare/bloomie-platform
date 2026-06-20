package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.DailyTrackingPersistenceEntity;

/**
 * Static assembler between daily tracking domain and persistence representations.
 */
public final class DailyTrackingPersistenceAssembler {

    private DailyTrackingPersistenceAssembler() {
    }

    /**
     * Converts a {@link DailyTrackingPersistenceEntity} into a {@link DailyTracking} domain aggregate.
     *
     * @param entity the JPA entity to convert
     * @return the reconstructed domain aggregate, or {@code null} if entity is {@code null}
     */
    public static DailyTracking toDomainFromPersistence(DailyTrackingPersistenceEntity entity) {
        if (entity == null) return null;
        var tracking = new DailyTracking();
        tracking.setId(entity.getId());
        tracking.setPatientId(entity.getPatientId());
        tracking.setRoutineId(entity.getRoutineId());
        tracking.setDate(entity.getDate());
        tracking.setCompleted(entity.isCompleted());
        tracking.setCompletedAt(entity.getCompletedAt());
        return tracking;
    }

    /**
     * Converts a {@link DailyTracking} domain aggregate into a {@link DailyTrackingPersistenceEntity}.
     *
     * @param tracking the domain aggregate to convert
     * @return the JPA entity ready for persistence, or {@code null} if tracking is {@code null}
     */
    public static DailyTrackingPersistenceEntity toPersistenceFromDomain(DailyTracking tracking) {
        if (tracking == null) return null;
        var entity = new DailyTrackingPersistenceEntity();
        if (tracking.getId() != null) {
            entity.setId(tracking.getId());
        }
        entity.setPatientId(tracking.getPatientId());
        entity.setRoutineId(tracking.getRoutineId());
        entity.setDate(tracking.getDate());
        entity.setCompleted(tracking.isCompleted());
        entity.setCompletedAt(tracking.getCompletedAt());
        return entity;
    }
}
