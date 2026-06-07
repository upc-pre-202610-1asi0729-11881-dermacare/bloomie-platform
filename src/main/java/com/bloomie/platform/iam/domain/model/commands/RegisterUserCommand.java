package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to register a new Young Adult user.
 *
 * <p>The {@code password} field carries the raw plain-text password at the interface layer
 * and the pre-hashed password when reconstructed inside the application service.</p>
 */
public record RegisterUserCommand(String email, String password, String firstName, String lastName) {
}
