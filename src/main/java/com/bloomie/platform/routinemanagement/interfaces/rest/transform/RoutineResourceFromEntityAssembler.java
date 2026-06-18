package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineItemResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineResource;

import java.util.Collections;
import java.util.List;

/**
 * Assembler that converts a {@link Routine} domain aggregate into a {@link RoutineResource}.
 */
public final class RoutineResourceFromEntityAssembler {

    private RoutineResourceFromEntityAssembler() {
    }

    public static RoutineResource toResourceFromEntity(Routine routine) {
        List<RoutineItemResource> itemResources = routine.getItems() != null
                ? routine.getItems().stream()
                        .map(item -> new RoutineItemResource(
                                item.getId(),
                                item.getStep(),
                                item.getOrder(),
                                item.getScheduledTime(),
                                item.getProductRecommendation()))
                        .toList()
                : Collections.emptyList();

        return new RoutineResource(
                routine.getId(),
                routine.getPatientId(),
                routine.getSkinAnalysisId(),
                routine.getStatus().name(),
                routine.getCreatedAt() != null ? routine.getCreatedAt().toString() : null,
                itemResources
        );
    }
}