package com.bloomie.platform.iam.domain.model.commands;

/**
 * Command to authenticate an existing user with email and password credentials.
 *
 * <p>The {@code password} field carries the raw plain-text password as supplied
 * by the user; the application service is responsible for verifying it against
 * the stored hash via {@link com.bloomie.platform.iam.application.internal.outboundservices.hashing.HashingService}.</p>
 */
public record SignInCommand(String email, String password) {
}
