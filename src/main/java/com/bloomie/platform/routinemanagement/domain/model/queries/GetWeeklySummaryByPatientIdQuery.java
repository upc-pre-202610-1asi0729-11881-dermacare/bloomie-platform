package com.bloomie.platform.routinemanagement.domain.model.queries;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;

/**
 * Query to retrieve the weekly routine completion summary for a specific patient.
 *
 * @param patientId the patient value object identifying the target patient
 */
public record GetWeeklySummaryByPatientIdQuery(PatientId patientId) {
}
