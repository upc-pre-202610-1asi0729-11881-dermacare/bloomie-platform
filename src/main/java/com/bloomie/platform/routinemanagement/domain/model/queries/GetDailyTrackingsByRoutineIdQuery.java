package com.bloomie.platform.routinemanagement.domain.model.queries;

/**
 * Query to retrieve all daily tracking entries for a specific routine.
 *
 * @param routineId the routine identifier
 */
public record GetDailyTrackingsByRoutineIdQuery(Long routineId) {
}
