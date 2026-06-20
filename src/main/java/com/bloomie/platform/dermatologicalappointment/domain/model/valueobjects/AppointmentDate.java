package com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects;

import java.time.LocalDateTime;

/**
 * Value object representing the scheduled date and time of a dermatological appointment.
 *
 * <p>Encapsulates the invariant that an appointment must always be placed at a
 * future point in time. A date set in the past or at the exact current moment
 * is considered invalid and will be rejected on construction.</p>
 *
 * @param value the appointment date and time; must not be {@code null} and must be in the future
 */
public record AppointmentDate(LocalDateTime value) {

    private static final String NULL_MESSAGE_KEY = "appointment.date.null";
    private static final String PAST_MESSAGE_KEY = "appointment.date.must.be.future";

    /**
     * Compact constructor that validates the appointment date.
     *
     * @throws IllegalArgumentException if the value is {@code null} or is not strictly after the current time
     */
    public AppointmentDate {
        if (value == null) {
            throw new IllegalArgumentException(NULL_MESSAGE_KEY);
        }
        if (!value.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException(PAST_MESSAGE_KEY);
        }
    }
}
