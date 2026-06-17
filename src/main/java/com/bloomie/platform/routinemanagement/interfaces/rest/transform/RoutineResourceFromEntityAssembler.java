package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineResource;

/**
 * Assembler that converts a {@link Routine} domain entity into a {@link RoutineResource}.
 */
public final class RoutineResourceFromEntityAssembler {

    private RoutineResourceFromEntityAssembler() {
    }

    public static RoutineResource toResourceFromEntity(Routine routine) {
        var createdAt = routine.getCreatedAt() != null
                ? routine.getCreatedAt().toInstant().toString()
                : null;
        return new RoutineResource(
                routine.getId(),
                routine.getUserId(),
                routine.getSkinProfileId(),
                routine.getFacialScanId(),
                routine.getStatus().name(),
                createdAt
        );
    }
}