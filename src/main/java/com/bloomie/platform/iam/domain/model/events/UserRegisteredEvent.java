package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised when a new Young Adult user has been successfully registered.
 *
 * @param userId the identifier of the newly created user
 * @param email  the email address used during registration
 */
public record UserRegisteredEvent(Long userId, String email) {

    public static UserRegisteredEvent from(User user) {
        return new UserRegisteredEvent(
                user.getId(),
                user.getEmail());
    }
}
