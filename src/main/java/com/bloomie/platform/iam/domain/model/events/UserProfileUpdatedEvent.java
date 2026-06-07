package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised when a user's profile (name or email) has been updated.
 *
 * @param userId the identifier of the user whose profile changed
 * @param email  the new email address after the update
 */
public record UserProfileUpdatedEvent(Long userId, String email) {

    /**
     * Creates a {@code UserProfileUpdatedEvent} from the given user aggregate.
     *
     * @param user the user whose profile was just updated
     * @return a new event carrying the user's id and updated email
     */
    public static UserProfileUpdatedEvent from(User user) {
        return new UserProfileUpdatedEvent(
                user.getId(),
                user.getEmail()
        );
    }
}
