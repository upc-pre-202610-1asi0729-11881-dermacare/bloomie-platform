package com.bloomie.platform.routinemanagement.interfaces.rest.resources;

/**
 * Resource representing a daily tracking entry in REST responses.
 *
 * @param id        the unique identifier of the daily tracking entry
 * @param routineId the identifier of the routine being tracked
 * @param userId    the identifier of the user who owns this tracking record
 * @param date      the ISO 8601 date string for this tracking entry (e.g. "2026-05-11")
 * @param status    the completion status of the routine on this day
 */
public record DailyTrackingResource(
        Long id,
        Long routineId,
        Long userId,
        String date,
        String status
) {
}