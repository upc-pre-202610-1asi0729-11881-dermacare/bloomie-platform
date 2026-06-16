package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to update the profile photo URL of an existing user.
 *
 * <p>{@code userId} is passed as {@link Long} because the controller path variable
 * is bound directly to {@code Long} — no string parsing is needed in the application service.</p>
 */
public record UpdateUserPhotoCommand(Long userId, String photoUrl) {
}
