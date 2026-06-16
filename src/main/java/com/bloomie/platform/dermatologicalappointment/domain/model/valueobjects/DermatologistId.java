package com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects;

/**
 * Value object representing a reference to a Dermatologist from the IAM bounded context.
 *
 * <p>Uses a {@code Long} internally, as required by the project convention when referencing
 * identities from other bounded contexts. The id must be a positive number to be valid.</p>
 *
 * @param dermatologistId the IAM user id of the dermatologist; must be a positive number
 */
public record DermatologistId(Long dermatologistId) {

    private static final String INVALID_MESSAGE_KEY = "appointment.dermatologist.id.invalid";

    /**
     * Compact constructor that validates the dermatologist id.
     *
     * @throws IllegalArgumentException if the dermatologistId is {@code null} or less than {@code 1}
     */
    public DermatologistId {
        if (dermatologistId == null || dermatologistId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
