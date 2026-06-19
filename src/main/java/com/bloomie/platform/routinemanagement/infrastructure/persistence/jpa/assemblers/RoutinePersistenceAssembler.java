package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutineItemPersistenceEntity;
import com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.entities.RoutinePersistenceEntity;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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
        routine.setPatientId(entity.getPatientId());
        routine.setSkinAnalysisId(entity.getSkinAnalysisId());
        routine.setStatus(entity.getStatus());
        routine.setSkinType(entity.getSkinType());
        if (entity.getCreatedAt() != null) {
            routine.setCreatedAt(entity.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
        routine.setItems(toItemsDomainFromPersistence(entity.getItems(), entity));
        return routine;
    }

    public static RoutinePersistenceEntity toPersistenceFromDomain(Routine routine) {
        if (routine == null) return null;
        var entity = new RoutinePersistenceEntity();
        entity.setId(routine.getId());
        entity.setPatientId(routine.getPatientIdValue());
        entity.setSkinAnalysisId(routine.getSkinAnalysisIdValue());
        entity.setStatus(routine.getStatus());
        entity.setSkinType(routine.getSkinType());
        if (routine.getItems() != null) {
            List<RoutineItemPersistenceEntity> itemEntities = new ArrayList<>();
            for (RoutineItem item : routine.getItems()) {
                var itemEntity = new RoutineItemPersistenceEntity();
                itemEntity.setId(item.getId());
                itemEntity.setRoutine(entity);
                itemEntity.setStep(item.getStep());
                itemEntity.setOrder(item.getOrder());
                itemEntity.setScheduledTime(item.getScheduledTime());
                itemEntity.setProductRecommendation(item.getProductRecommendation());
                itemEntities.add(itemEntity);
            }
            entity.setItems(itemEntities);
        }
        return entity;
    }

    private static List<RoutineItem> toItemsDomainFromPersistence(
            List<RoutineItemPersistenceEntity> itemEntities,
            RoutinePersistenceEntity routineEntity) {
        if (itemEntities == null) return new ArrayList<>();
        return itemEntities.stream()
                .map(ie -> new RoutineItem(ie.getId(), ie.getStep(), ie.getOrder(), ie.getScheduledTime(), ie.getProductRecommendation()))
                .toList();
    }
}