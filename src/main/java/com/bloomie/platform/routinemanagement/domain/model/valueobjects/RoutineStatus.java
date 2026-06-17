package com.bloomie.platform.routinemanagement.domain.model.valueobjects;

/**
 * Enumeration representing the lifecycle status of a routine.
 */
public enum RoutineStatus {
    /**
     * The routine is currently active and being followed by the user.
     */
    ACTIVE,

    /**
     * The routine is being updated (e.g. product replacement in progress).
     */
    UPDATE,

    /**
     * The routine is no longer active.
     */
    INACTIVE
}