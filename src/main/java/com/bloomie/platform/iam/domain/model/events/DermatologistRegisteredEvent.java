package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised when a new Dermatologist user has been successfully registered.
 *
 * @param userId the identifier of the newly created dermatologist user
 * @param email  the email address used during registration
 */
/**
 * Domain event raised when a new Dermatologist user has been successfully registered.
 *
 * @param userId    the identifier of the newly created dermatologist user
 * @param firstName the dermatologist's first name
 * @param lastName  the dermatologist's last name
 * @param email     the email address used during registration
 */
public record DermatologistRegisteredEvent(Long userId, String firstName, String lastName, String email) {
    public static DermatologistRegisteredEvent from(User user) {
        return new DermatologistRegisteredEvent(
                user.getId(),
                user.getName().firstName(),
                user.getName().lastName(),
                user.getEmail());
    }
}
