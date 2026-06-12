package com.bloomie.platform.dermatologicalAppointment.domain.model.valueobjects;

/**
 * Value object representing clinical notes (observations, diagnosis, or recommendations)
 * written by the dermatologist during a consultation.
 *
 * <p>Notes may be blank during progressive saving but must not exceed 5 000 characters.
 * A {@code null} input is normalised to an empty string.</p>
 *
 * @param value the notes text; {@code null} is treated as empty
 */
public record ClinicalNotes(String value) {

    private static final int MAX_LENGTH = 5000;
    private static final String TOO_LONG_KEY = "appointment.clinical.notes.too.long";

    public ClinicalNotes {
        if (value == null) {
            value = "";
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(TOO_LONG_KEY);
        }
    }
}
