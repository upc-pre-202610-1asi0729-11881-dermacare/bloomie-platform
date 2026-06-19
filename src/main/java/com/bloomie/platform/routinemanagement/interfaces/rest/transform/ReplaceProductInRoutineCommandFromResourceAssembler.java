package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.commands.ReplaceProductInRoutineCommand;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.ReplaceProductInRoutineResource;

/**
 * Assembler to convert a {@link ReplaceProductInRoutineResource} to a {@link ReplaceProductInRoutineCommand}.
 */
public class ReplaceProductInRoutineCommandFromResourceAssembler {

    /**
     * Converts a ReplaceProductInRoutineResource to a ReplaceProductInRoutineCommand.
     *
     * @param routineId     the routine identifier from the path
     * @param routineItemId the routine item identifier from the path
     * @param resource      the request resource
     * @return the assembled command
     */
    public static ReplaceProductInRoutineCommand toCommandFromResource(
            Long routineId, Long routineItemId, ReplaceProductInRoutineResource resource) {
        return new ReplaceProductInRoutineCommand(routineId, routineItemId, resource.newProductRecommendation());
    }
}
