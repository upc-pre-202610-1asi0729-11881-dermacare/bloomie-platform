package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing a daily tracking entry in REST responses.
 *
 * @param id          the unique identifier of the daily tracking entry
 * @param patientId   the identifier of the patient who owns this tracking record
 * @param routineId   the identifier of the routine that was completed
 * @param date        the ISO 8601 date string for this tracking entry (e.g. "2026-06-19")
 * @param isCompleted whether the routine was completed on this day
 * @param completedAt the ISO 8601 date-time string when the completion was recorded
 */
public record DailyTrackingResource(
        Long id,
        Long patientId,
        Long routineId,
        String date,
        boolean isCompleted,
        String completedAt) {
}
