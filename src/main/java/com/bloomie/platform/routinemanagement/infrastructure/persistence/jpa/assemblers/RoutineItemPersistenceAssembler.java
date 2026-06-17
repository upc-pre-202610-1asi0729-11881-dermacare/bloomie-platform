package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutineItemPersistenceEntity;

/**
 * Static assembler between routine item domain and persistence representations.
 */
public final class RoutineItemPersistenceAssembler {

    private RoutineItemPersistenceAssembler() {
    }

    public static RoutineItem toDomainFromPersistence(RoutineItemPersistenceEntity entity) {
        if (entity == null) return null;
        var item = new RoutineItem();
        item.setId(entity.getId());
        item.setRoutineId(entity.getRoutineId());
        item.setProductId(entity.getProductId());
        item.setStep(entity.getStep());
        item.setScheduledTime(entity.getScheduledTime());
        item.setStatus(entity.getStatus());
        item.setOrder(entity.getOrder());
        return item;
    }

    public static RoutineItemPersistenceEntity toPersistenceFromDomain(RoutineItem item) {
        if (item == null) return null;
        var entity = new RoutineItemPersistenceEntity();
        if (item.getId() != null) {
            entity.setId(item.getId());
        }
        entity.setRoutineId(item.getRoutineId());
        entity.setProductId(item.getProductId());
        entity.setStep(item.getStep());
        entity.setScheduledTime(item.getScheduledTime());
        entity.setStatus(item.getStatus());
        entity.setOrder(item.getOrder());
        return entity;
    }
}