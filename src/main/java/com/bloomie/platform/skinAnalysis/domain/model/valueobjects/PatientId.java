package com.bloomie.platform.skinAnalysis.domain.model.valueobjects;

/**
 * Value object that holds a reference to a patient identity from the IAM bounded context.
 *
 * @param patient_id the IAM user id of the patient; must be a positive number
 */
public record PatientId(Long patient_id) {

    private static final String INVALID_MESSAGE_KEY = "skin_analysis.patient.id.invalid";

    public PatientId {
        if (patient_id == null || patient_id < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
