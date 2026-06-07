package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to update an existing user's personal profile.
 *
 * <p>{@code userId} is passed as a {@link String} because it originates from the URL path
 * variable; the application service converts it to {@link Long} before querying
 * the repository.</p>
 */
public record UpdateUserProfileCommand(String userId, String firstName, String lastName, String email) {
}
