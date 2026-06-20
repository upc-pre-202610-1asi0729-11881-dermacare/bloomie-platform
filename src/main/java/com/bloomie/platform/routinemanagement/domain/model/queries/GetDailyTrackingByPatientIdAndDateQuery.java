package com.bloomie.platform.routinemanagement.domain.model.queries;

import java.time.LocalDate;

/**
 * Query to retrieve a daily tracking entry for a specific patient and date.
 *
 * @param patientId the patient identifier
 * @param date      the date to look up
 */
public record GetDailyTrackingByPatientIdAndDateQuery(Long patientId, LocalDate date) {
}
