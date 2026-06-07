package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.UpdateUserProfileCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.UpdateUserProfileResource;

/**
 * Converts an {@link UpdateUserProfileResource} REST payload (plus the URL path variable)
 * into an {@link UpdateUserProfileCommand}.
 */
public class UpdateUserProfileCommandFromResourceAssembler {
    public static UpdateUserProfileCommand toCommandFromResource(String userId, UpdateUserProfileResource resource) {
        return new UpdateUserProfileCommand(
                userId,
                resource.firstName(),
                resource.lastName(),
                resource.email()
        );
    }
}
