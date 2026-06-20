package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.RegisterDermatologistCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterDermatologistResource;

/** Converts a {@link RegisterDermatologistResource} REST payload into a {@link RegisterDermatologistCommand}. */
public class RegisterDermatologistCommandFromResourceAssembler {
    public static RegisterDermatologistCommand toCommandFromResource(RegisterDermatologistResource resource) {
        return new RegisterDermatologistCommand(
                resource.email(),
                resource.password(),
                resource.firstName(),
                resource.lastName()
        );
    }
}
