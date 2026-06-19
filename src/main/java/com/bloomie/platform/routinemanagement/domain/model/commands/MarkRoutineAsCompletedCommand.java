package com.bloomie.platform.routinemanagement.domain.model.commands;

import java.time.LocalDate;

/**
 * Command to mark a routine as completed for a specific date.
 *
 * @param patientId the patient identifier. Cannot be null or less than 1
 * @param routineId the routine identifier. Cannot be null or less than 1
 * @param date      the date on which the routine was completed. Cannot be null
 */
public record MarkRoutineAsCompletedCommand(Long patientId, Long routineId, LocalDate date) {

    /**
     * Compact constructor that validates command parameters.
     *
     * @throws IllegalArgumentException if patientId is null or less than 1
     * @throws IllegalArgumentException if routineId is null or less than 1
     * @throws IllegalArgumentException if date is null
     */
    public MarkRoutineAsCompletedCommand {
        if (patientId == null || patientId < 1)
            throw new IllegalArgumentException("daily.tracking.patient.id.invalid");
        if (routineId == null || routineId < 1)
            throw new IllegalArgumentException("daily.tracking.routine.id.invalid");
        if (date == null)
            throw new IllegalArgumentException("date cannot be null");
    }
}
