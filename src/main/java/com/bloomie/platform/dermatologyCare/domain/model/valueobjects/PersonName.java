package com.bloomie.platform.dermatologyCare.domain.model.valueobjects;

/**
 * Value object representing the full name of a dermatologist.
 *
 * <p>Private to the Dermatology Care bounded context — not shared with other BCs.</p>
 *
 * @param firstName The first name. Must not be null or blank.
 * @param lastName  The last name. Must not be null or blank.
 */
public record PersonName(String firstName, String lastName) {

    private static final String FIRST_NAME_BLANK_KEY = "dermatology.first.name.blank";
    private static final String LAST_NAME_BLANK_KEY = "dermatology.last.name.blank";

    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException(FIRST_NAME_BLANK_KEY);
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException(LAST_NAME_BLANK_KEY);
        }
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
