package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.commands.UpdateUserPhotoCommand;
import com.bloomie.platform.iam.interfaces.rest.resources.UpdateUserPhotoResource;

/**
 * Converts an {@link UpdateUserPhotoResource} REST payload (plus the URL path variable)
 * into an {@link UpdateUserPhotoCommand}.
 */
public class UpdateUserPhotoCommandFromResourceAssembler {

    public static UpdateUserPhotoCommand toCommandFromResource(Long userId, UpdateUserPhotoResource resource) {
        return new UpdateUserPhotoCommand(userId, resource.photoUrl());
    }
}
