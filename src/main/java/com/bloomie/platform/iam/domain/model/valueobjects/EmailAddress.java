package com.bloomie.platform.iam.domain.model.valueobjects;

import jakarta.validation.constraints.Email;

import java.util.regex.Pattern;

/**
 * EmailAddress Value Object.
 */
public record EmailAddress(@Email String address) {
    private static final String NOT_BLANK_MESSAGE_KEY = "user.email.blank";
    private static final String INVALID_MESSAGE_KEY = "user.email.invalid";


    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public EmailAddress {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }

    public String getAddress() {
        return address;
    }
}