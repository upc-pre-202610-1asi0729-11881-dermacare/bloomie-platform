package com.bloomie.platform.dermatologycare.interfaces.rest.transform;

import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateDermatologistProfileCommand;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.UpdateDermatologistProfileResource;

/**
 * Stateless assembler that builds an {@link UpdateDermatologistProfileCommand}
 * from a path variable and the request {@link UpdateDermatologistProfileResource}.
 */
public final class UpdateDermatologistProfileCommandFromResourceAssembler {

    private UpdateDermatologistProfileCommandFromResourceAssembler() {}

    public static UpdateDermatologistProfileCommand toCommandFromResource(
            Long profileId, UpdateDermatologistProfileResource resource) {
        return new UpdateDermatologistProfileCommand(
                profileId,
                resource.firstName(),
                resource.lastName(),
                resource.specialty(),
                resource.licenseNumber(),
                resource.phone(),
                resource.biography());
    }
}
