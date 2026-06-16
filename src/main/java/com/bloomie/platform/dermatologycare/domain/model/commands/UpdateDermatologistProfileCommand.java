package com.bloomie.platform.dermatologycare.domain.model.commands;

/**
 * Command to update an existing {@code DermatologistProfile}'s professional details.
 *
 * @param dermatologistProfileId the profile's own persistence id
 * @param firstName              updated first name
 * @param lastName               updated last name
 * @param specialty              updated medical specialty
 * @param licenseNumber          updated license number
 * @param phone                  updated contact phone
 * @param biography              updated biography
 * @param consultationFee        updated consultation fee; must not be negative
 */
public record UpdateDermatologistProfileCommand(
        Long dermatologistProfileId,
        String firstName,
        String lastName,
        String specialty,
        String licenseNumber,
        String phone,
        String biography,
        Double consultationFee) {
}
