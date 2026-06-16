package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised when a user's profile photo URL has been updated.
 *
 * @param userId   the identifier of the user whose photo changed
 * @param photoUrl the new photo URL after the update
 */
public record UserPhotoUpdatedEvent(Long userId, String photoUrl) {

    /**
     * Creates a {@code UserPhotoUpdatedEvent} from the given user aggregate.
     *
     * @param user the user whose photo was just updated
     * @return a new event carrying the user's id and updated photo URL
     */
    public static UserPhotoUpdatedEvent from(User user) {
        return new UserPhotoUpdatedEvent(
                user.getId(),
                user.getPhotoUrl()
        );
    }
}
