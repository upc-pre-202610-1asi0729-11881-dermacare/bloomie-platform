package com.bloomie.platform.skinanalysis.domain.model.queries;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;

/**
 * Query to retrieve all skin analyses belonging to a patient.
 *
 * @param patientId the patient value object
 */
public record GetSkinAnalysesByPatientIdQuery(PatientId patientId) {
}
