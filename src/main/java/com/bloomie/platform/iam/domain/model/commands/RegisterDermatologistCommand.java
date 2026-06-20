package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to register a new Dermatologist user.
 *
 * <p>Structurally identical to {@link RegisterUserCommand} but semantically distinct —
 * the application service assigns the {@code ROLE_DERMATOLOGIST} role instead of
 * the default {@code ROLE_YOUNG_ADULT}.</p>
 */
public record RegisterDermatologistCommand(String email, String password, String firstName, String lastName) {
}
