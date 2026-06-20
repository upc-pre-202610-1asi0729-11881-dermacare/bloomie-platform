package com.bloomie.platform.routinemanagement.domain.model.commands;

/**
 * Command to remove an optional product step from an active routine.
 *
 * @param routineId     the routine identifier. Cannot be null or less than 1
 * @param routineItemId the routine item identifier to remove. Cannot be null or less than 1
 */
public record RemoveProductFromRoutineCommand(Long routineId, Long routineItemId) {

    /**
     * Compact constructor that validates command parameters.
     *
     * @throws IllegalArgumentException if routineId is null or less than 1
     * @throws IllegalArgumentException if routineItemId is null or less than 1
     */
    public RemoveProductFromRoutineCommand {
        if (routineId == null || routineId <= 0)
            throw new IllegalArgumentException("routineId cannot be null or less than 1");
        if (routineItemId == null || routineItemId <= 0)
            throw new IllegalArgumentException("routineItemId cannot be null or less than 1");
    }
}
