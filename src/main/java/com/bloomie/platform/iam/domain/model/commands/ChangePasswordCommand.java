package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to change a user's password.
 *
 * <p>{@code currentPassword} is the raw plain-text password the caller claims to be the
 * current one; the application service verifies it before hashing {@code newPassword}
 * and delegating to the aggregate.</p>
 */
public record ChangePasswordCommand(String userId, String currentPassword, String newPassword) {
}
