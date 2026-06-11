package com.bloomie.platform.dermatologyCare.domain.model.valueobjects;

/**
 * Value object representing a dermatologist's contact phone number.
 *
 * <p>Must contain only digits and be between 7 and 15 characters long.</p>
 */
public record ContactPhone(String contactPhone) {

    private static final String CONTACT_PHONE_BLANK_KEY = "dermatology.contact.phone.blank";
    private static final String CONTACT_PHONE_LENGTH_KEY = "dermatology.contact.phone.long";
    private static final String CONTACT_PHONE_DIGITS_KEY = "dermatology.contact.phone.digits.only";

    public ContactPhone {
        if (contactPhone == null || contactPhone.isBlank()) {
            throw new IllegalArgumentException(CONTACT_PHONE_BLANK_KEY);
        }
        if (contactPhone.length() < 7 || contactPhone.length() > 15) {
            throw new IllegalArgumentException(CONTACT_PHONE_LENGTH_KEY);
        }
        if (!contactPhone.matches("\\d+")) {
            throw new IllegalArgumentException(CONTACT_PHONE_DIGITS_KEY);
        }
    }
}
