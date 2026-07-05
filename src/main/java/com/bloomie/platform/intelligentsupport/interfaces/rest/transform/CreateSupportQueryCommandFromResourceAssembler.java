package com.bloomie.platform.intelligentsupport.interfaces.rest.transform;

import com.bloomie.platform.intelligentsupport.domain.model.commands.CreateSupportQueryCommand;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.CreateSupportQueryResource;

/**
 * Converts a {@link CreateSupportQueryResource} request body into a {@link CreateSupportQueryCommand}.
 */
public final class CreateSupportQueryCommandFromResourceAssembler {

    private CreateSupportQueryCommandFromResourceAssembler() {}

    public static CreateSupportQueryCommand toCommandFromResource(CreateSupportQueryResource resource) {
        return new CreateSupportQueryCommand(
                resource.patientId(),
                resource.skinProfileId()
        );
    }
}