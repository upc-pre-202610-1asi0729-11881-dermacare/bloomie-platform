package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.SignInCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.SignInResource;

/** Converts a {@link SignInResource} REST payload into a {@link SignInCommand}. */
public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.email(),
                resource.password()
        );
    }
}
