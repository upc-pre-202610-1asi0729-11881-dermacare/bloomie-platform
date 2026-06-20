package com.bloomie.platform.routinemanagement.domain.model.queries;

/**
 * Query to retrieve all daily tracking entries for a specific patient.
 *
 * @param patientId the patient identifier
 */
public record GetDailyTrackingsByPatientIdQuery(Long patientId) {
}
