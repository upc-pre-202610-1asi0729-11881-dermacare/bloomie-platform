package com.bloomie.platform.dermatologyCare.domain.model.commands;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;

/**
 * Command to create a minimal {@code DermatologistProfile} upon IAM registration.
 * Specialty, license number, and contact phone are filled in later via
 * {@link UpdateDermatologistProfileCommand}.
 */
public record RegisterDermatologistProfileCommand(
        DermatologistId dermatologistId,
        String firstName,
        String lastName) {
}
