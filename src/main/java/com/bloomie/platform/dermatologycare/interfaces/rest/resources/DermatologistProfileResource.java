package com.bloomie.platform.dermatologycare.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(name = "DermatologistProfile", description = "Dermatologist profile response")
public record DermatologistProfileResource(
        @Schema(description = "Profile id") Long id,
        @Schema(description = "IAM user id of the dermatologist") Long dermatologistId,
        @Schema(description = "First name") String firstName,
        @Schema(description = "Last name") String lastName,
        @Schema(description = "Medical specialty") String specialtyName,
        @Schema(description = "License number") String licenseNumber,
        @Schema(description = "Contact phone") String contactPhone,
        @Schema(description = "Professional biography") String biography,
        @Schema(description = "Consultation fee", example = "0.0") Double consultationFee) {
}
