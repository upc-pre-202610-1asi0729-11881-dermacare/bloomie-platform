package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.GeneratePersonalizedRoutineResource;

/**
 * Assembler that converts a {@link GeneratePersonalizedRoutineResource} into a {@link GeneratePersonalizedRoutineCommand}.
 */
public final class GeneratePersonalizedRoutineCommandFromResourceAssembler {

    private GeneratePersonalizedRoutineCommandFromResourceAssembler() {
    }

    public static GeneratePersonalizedRoutineCommand toCommandFromResource(GeneratePersonalizedRoutineResource resource) {
        return new GeneratePersonalizedRoutineCommand(
                resource.userId(),
                resource.skinProfileId(),
                resource.facialScanId(),
                RoutineStatus.valueOf(resource.status())
        );
    }
}