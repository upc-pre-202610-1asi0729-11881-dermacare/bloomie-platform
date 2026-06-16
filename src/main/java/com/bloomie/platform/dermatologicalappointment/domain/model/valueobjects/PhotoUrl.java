package com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Value object representing the URL of a clinical photo uploaded during a consultation.
 *
 * <p>The URL must point to a valid HTTP or HTTPS resource so that the photo can be
 * retrieved by any authorised consumer of this bounded context's data.</p>
 *
 * @param value the photo URL string; must not be blank and must be a valid http/https URL
 */
public record PhotoUrl(String value) {

    private static final String BLANK_MESSAGE_KEY = "appointment.photo.url.blank";
    private static final String INVALID_MESSAGE_KEY = "appointment.photo.url.invalid";

    private static final Pattern URL_PATTERN = Pattern.compile("^https?://[^\\s]+$");

    /**
     * Compact constructor that validates the photo URL.
     *
     * @throws IllegalArgumentException if the value is {@code null}, blank, or not a valid http/https URL
     */
    public PhotoUrl {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(BLANK_MESSAGE_KEY);
        }
        if (!URL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}
