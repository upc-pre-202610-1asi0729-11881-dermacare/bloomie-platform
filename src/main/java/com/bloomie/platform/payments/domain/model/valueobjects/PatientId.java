package com.bloomie.platform.payments.domain.model.valueobjects;

/**
 * Value object representing a reference to a Young Adult patient from the IAM bounded context.
 *
 * <p>Uses a {@code Long} internally, as required by the project convention when referencing
 * identities from other bounded contexts. The id must be a positive number to be valid.</p>
 *
 * @param patientId the IAM user id of the patient; must be a positive number
 */
public record PatientId(Long patientId) {

    private static final String INVALID_MESSAGE_KEY = "appointment.patient.id.invalid";

    /**
     * Compact constructor that validates the patient id.
     *
     * @throws IllegalArgumentException if the patientId is {@code null} or less than {@code 1}
     */
    public PatientId {
        if (patientId == null || patientId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
