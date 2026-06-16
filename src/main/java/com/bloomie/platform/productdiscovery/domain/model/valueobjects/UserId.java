package com.bloomie.platform.productdiscovery.domain.model.valueobjects;

/**
 * Value object representing the user identifier.
 *
 * <p>
 * This value object is used to link a favorite product
 * to a user from the IAM bounded context.
 * It must be a positive Long value.
 * </p>
 *
 * @param userId The user identifier. It cannot be null or less than 1.
 */
public record UserId(Long userId) {

    /**
     * Compact constructor for UserId.
     * Validates that the userId is not null and is greater than 0.
     * @throws IllegalArgumentException if the userId is null or less than 1.
     */
    public UserId {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId cannot be null or less than 1");
        }
    }
}