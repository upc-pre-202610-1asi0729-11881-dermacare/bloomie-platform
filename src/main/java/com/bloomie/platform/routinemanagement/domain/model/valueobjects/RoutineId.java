package com.bloomie.platform.routinemanagement.domain.model.valueobjects;

/**
 * Value object that holds a reference to a routine identity within the Routine Management bounded context.
 *
 * @param routineId the routine id; must be a positive number
 */
public record RoutineId(Long routineId) {

    private static final String INVALID_MESSAGE_KEY = "daily.tracking.routine.id.invalid";

    public RoutineId {
        if (routineId == null || routineId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
