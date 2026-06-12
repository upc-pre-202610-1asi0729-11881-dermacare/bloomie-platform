package com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Value object that wraps a dermatological appointment date-time as an ISO-8601 string.
 *
 * <p>Validates that the value is non-blank and parses as a valid ISO-8601 local date-time
 * (e.g. {@code "2025-12-25T10:00:00"}). Future-date validation is an application-level
 * business rule enforced by the {@code Appointment} aggregate at scheduling time, not here,
 * so that past dates stored in the database can be safely reconstituted without errors.</p>
 */
public record AppointmentDateTime(String value) {

    private static final String BLANK_KEY = "appointment.scheduled.at.blank";
    private static final String INVALID_FORMAT_KEY = "appointment.scheduled.at.invalid";

    public AppointmentDateTime {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(BLANK_KEY);
        }
        try {
            LocalDateTime.parse(value);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(INVALID_FORMAT_KEY);
        }
    }
}
