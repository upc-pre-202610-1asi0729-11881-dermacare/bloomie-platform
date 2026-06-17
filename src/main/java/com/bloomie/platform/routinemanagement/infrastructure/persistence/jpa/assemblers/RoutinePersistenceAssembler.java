package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutinePersistenceEntity;

/**
 * Static assembler between routine domain and persistence representations.
 */
public final class RoutinePersistenceAssembler {

    private RoutinePersistenceAssembler() {
    }

    public static Routine toDomainFromPersistence(RoutinePersistenceEntity entity) {
        if (entity == null) return null;
        var routine = new Routine();
        routine.setId(entity.getId());
        routine.setUserId(entity.getUserId());
        routine.setSkinProfileId(entity.getSkinProfileId());
        routine.setFacialScanId(entity.getFacialScanId());
        routine.setStatus(entity.getStatus());
        routine.setCreatedAt(entity.getCreatedAt());
        return routine;
    }

    public static RoutinePersistenceEntity toPersistenceFromDomain(Routine routine) {
        if (routine == null) return null;
        var entity = new RoutinePersistenceEntity();
        if (routine.getId() != null) {
            entity.setId(routine.getId());
        }
        entity.setUserId(routine.getUserId());
        entity.setSkinProfileId(routine.getSkinProfileId());
        entity.setFacialScanId(routine.getFacialScanId());
        entity.setStatus(routine.getStatus());
        return entity;
    }
}