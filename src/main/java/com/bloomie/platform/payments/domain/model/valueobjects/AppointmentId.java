package com.bloomie.platform.payments.domain.model.valueobjects;

/**
 * Value object representing a reference to a dermatological appointment from the
 * Dermatological Appointment bounded context.
 *
 * <p>Uses a {@code Long} internally, as required by the project convention when referencing
 * identities from other bounded contexts. The id must be a positive number to be valid.</p>
 *
 * @param appointmentId the appointment identifier; must be a positive number
 */
public record AppointmentId(Long appointmentId) {

    private static final String INVALID_MESSAGE_KEY = "payment.appointment.id.invalid";

    /**
     * Compact constructor that validates the appointment id.
     *
     * @throws IllegalArgumentException if the appointmentId is {@code null} or less than {@code 1}
     */
    public AppointmentId {
        if (appointmentId == null || appointmentId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}
