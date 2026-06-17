package com.bloomie.platform.skinanalysis.domain.model.queries;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

/**
 * Query to retrieve the skin profile belonging to a specific patient.
 *
 * @param patientId the patient value object whose skin profile is requested
 */
public record GetSkinProfileByPatientIdQuery(PatientId patientId) {
}
