package com.bloomie.platform.iam.domain.model.events;

import com.bloomie.platform.iam.domain.model.aggregates.User;

/**
 * Domain event raised after a user's password has been successfully changed.
 *
 * <p>The new password is deliberately not included in the event payload to
 * avoid leaking sensitive credential data through the event bus.</p>
 *
 * @param userId the identifier of the user who changed their password
 */
public record PasswordChangedEvent(Long userId) {
    public static PasswordChangedEvent from(User user){
        return new PasswordChangedEvent(
                user.getId());
    }
}
