package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.RegisterUserCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterUserResource;

/** Converts a {@link RegisterUserResource} REST payload into a {@link RegisterUserCommand}. */
public class RegisterUserCommandFromResourceAssembler {
    public static RegisterUserCommand toCommandFromResource(RegisterUserResource resource) {
        return new RegisterUserCommand(
                resource.firstName(),
                resource.lastName(),
                resource.email(),
                resource.password()
        );
    }
}
