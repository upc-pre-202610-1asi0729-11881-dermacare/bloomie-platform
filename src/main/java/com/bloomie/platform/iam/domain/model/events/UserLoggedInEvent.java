package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised after a user has authenticated successfully.
 *
 * @param userId the identifier of the user who signed in
 * @param email  the email address used to authenticate
 */
public record UserLoggedInEvent(Long userId, String email) {
    public static UserLoggedInEvent from(User user){
        return new UserLoggedInEvent(
                user.getId(),
                user.getEmail()
        );
    }
}
