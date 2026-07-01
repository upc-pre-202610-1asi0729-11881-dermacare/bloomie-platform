package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.ChangePasswordCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.ChangePasswordResource;

/**
 * Converts a {@link ChangePasswordResource} REST payload (plus the URL path variable)
 * into a {@link ChangePasswordCommand}.
 */
public class ChangePasswordCommandFromResourceAssembler {
    public static ChangePasswordCommand toCommandFromResource(String userId, ChangePasswordResource resource) {
        return new ChangePasswordCommand(
                userId,
                resource.currentPassword(),
                resource.newPassword()
        );
    }
}
