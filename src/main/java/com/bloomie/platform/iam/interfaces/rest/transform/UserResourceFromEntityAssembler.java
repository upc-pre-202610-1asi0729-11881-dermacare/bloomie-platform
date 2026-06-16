package com.bloomie.platform.iam.interfaces.rest.transform;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.interfaces.rest.resources.UserResource;

/** Converts a {@link User} domain aggregate into a {@link UserResource} REST response payload. */
public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream()
                .map(Role::getStringName)
                .toList();
        return new UserResource(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                roles,
                user.getPhotoUrl()
        );
    }
}
