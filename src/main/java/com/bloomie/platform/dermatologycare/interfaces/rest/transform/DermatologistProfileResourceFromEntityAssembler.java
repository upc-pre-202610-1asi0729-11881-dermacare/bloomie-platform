package com.bloomie.platform.dermatologycare.interfaces.rest.transform;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.DermatologistProfileResource;

/**
 * Stateless assembler that converts a {@link DermatologistProfile} aggregate
 * into a {@link DermatologistProfileResource} response DTO.
 */
public final class DermatologistProfileResourceFromEntityAssembler {

    private DermatologistProfileResourceFromEntityAssembler() {}

    public static DermatologistProfileResource toResourceFromEntity(DermatologistProfile profile) {
        return new DermatologistProfileResource(
                profile.getId(),
                profile.getDermatologistId(),
                profile.getName().firstName(),
                profile.getName().lastName(),
                profile.getSpecialty() != null ? profile.getSpecialty().specialtyName() : null,
                profile.getLicenseNumber() != null ? profile.getLicenseNumber().licenseNumber() : null,
                profile.getContactPhone() != null ? profile.getContactPhone().contactPhone() : null,
                profile.getBiography(),
                profile.getConsultationFee());
    }
}
