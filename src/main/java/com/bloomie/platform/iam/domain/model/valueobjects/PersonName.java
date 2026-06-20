package com.bloomie.platform.iam.domain.model.valueobjects;

/**
 * PersonName Value Object.
 */
public record PersonName(String firstName, String lastName) {

    private static final String FIRST_NAME_NOT_BLANK_MESSAGE_KEY = "user.first.name.blank";
    private static final String LAST_NAME_NOT_BLANK_MESSAGE_KEY = "user.last.name.blank";
    /**
     * Full name getter
     * @return Full name
     */
    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }

    /**
     * Constructor with validation
     * @param firstName First name
     * @param lastName Last name
     */
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException(FIRST_NAME_NOT_BLANK_MESSAGE_KEY);
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException(LAST_NAME_NOT_BLANK_MESSAGE_KEY);
        }
    }
}