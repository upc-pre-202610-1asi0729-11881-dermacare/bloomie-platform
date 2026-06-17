package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineItemResource;

/**
 * Assembler that converts a {@link RoutineItem} domain entity into a {@link RoutineItemResource}.
 */
public final class RoutineItemResourceFromEntityAssembler {

    private RoutineItemResourceFromEntityAssembler() {
    }

    public static RoutineItemResource toResourceFromEntity(RoutineItem item) {
        return new RoutineItemResource(
                item.getId(),
                item.getRoutineId(),
                item.getProductId(),
                item.getStep().name(),
                item.getScheduledTime(),
                item.getStatus().name(),
                item.getOrder()
        );
    }
}