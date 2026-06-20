package com.bloomie.platform.routinemanagement.domain.model.valueobjects;

/**
 * Value object that holds a reference to a patient identity from the IAM bounded context.
 *
 * @param patientId the IAM user id of the patient; must be a positive number
 */
public record PatientId(Long patientId) {

    private static final String INVALID_MESSAGE_KEY = "routine.patient.id.invalid";

    public PatientId {
        if (patientId == null || patientId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
