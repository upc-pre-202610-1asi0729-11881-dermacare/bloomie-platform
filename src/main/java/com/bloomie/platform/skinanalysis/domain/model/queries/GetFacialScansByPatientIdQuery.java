package com.bloomie.platform.skinanalysis.domain.model.queries;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

/**
 * Query to retrieve all facial scans belonging to a specific patient.
 *
 * @param patientId the patient value object whose facial scans are requested
 */
public record GetFacialScansByPatientIdQuery(PatientId patientId) {
}
