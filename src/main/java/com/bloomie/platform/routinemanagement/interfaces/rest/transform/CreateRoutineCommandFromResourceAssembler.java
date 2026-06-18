package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.commands.CreateRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.CreateRoutineResource;

/**
 * Assembler that converts a {@link CreateRoutineResource} into a {@link CreateRoutineCommand}.
 */
public final class CreateRoutineCommandFromResourceAssembler {

    private CreateRoutineCommandFromResourceAssembler() {
    }

    public static CreateRoutineCommand toCommandFromResource(CreateRoutineResource resource) {
        return new CreateRoutineCommand(
                resource.userId(),
                resource.skinProfileId(),
                resource.facialScanId(),
                RoutineStatus.valueOf(resource.status())
        );
    }
}