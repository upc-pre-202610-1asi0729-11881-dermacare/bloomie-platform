package com.bloomie.platform.routinemanagement.interfaces.rest.transform;

import com.bloomie.platform.routinemanagement.domain.model.commands.MarkRoutineAsCompletedCommand;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.MarkRoutineAsCompletedResource;

import java.time.LocalDate;

/**
 * Assembler that converts a {@link MarkRoutineAsCompletedResource} into a {@link MarkRoutineAsCompletedCommand}.
 */
public final class MarkRoutineAsCompletedCommandFromResourceAssembler {

    private MarkRoutineAsCompletedCommandFromResourceAssembler() {
    }

    /**
     * Converts the REST resource into the application command.
     *
     * <p>The {@code date} string is parsed using ISO 8601 format (e.g. "2026-06-19").</p>
     *
     * @param resource the incoming REST resource
     * @return a new {@link MarkRoutineAsCompletedCommand}
     */
    public static MarkRoutineAsCompletedCommand toCommandFromResource(MarkRoutineAsCompletedResource resource) {
        var date = LocalDate.parse(resource.date());
        return new MarkRoutineAsCompletedCommand(resource.patientId(), resource.routineId(), date);
    }
}
