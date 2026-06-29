package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to verify and seed all system roles on application startup.
 * Handled by {@link com.bloomie.platform.iam.application.commandservices.RoleCommandService}.
 */
public record SeedRolesCommand() {
}
