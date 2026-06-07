package com.bloomie.platform.iam.domain.model.valueobjects;

/**
 * Value object wrapping a BCrypt-hashed password string.
 *
 * <p>The domain never stores or receives raw passwords; callers must hash the
 * raw input via {@link com.bloomie.platform.iam.application.internal.outboundservices.hashing.HashingService}
 * before constructing this value object.</p>
 */
public record HashedPassword(String hashedPassword) {
    public static final String NOT_BLANK_MESSAGE_KEY = "user.password.blank";

    public HashedPassword {
        if (hashedPassword == null || hashedPassword.isBlank()){
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
    }
    public String value() {return hashedPassword;}
}
