package com.bloomie.platform.iam.application.commandservices;

import com.bloomie.platform.iam.domain.model.commands.SeedRolesCommand;

/**
 * Application service port for write operations on roles.
 *
 * <p>The only command at the moment is {@link SeedRolesCommand}, which
 * ensures every {@link com.bloomie.platform.iam.domain.model.valueobjects.UserRole}
 * value has a matching row in the roles table.</p>
 */
public interface RoleCommandService {

    /** Verifies that all roles exist in the database and inserts any that are missing. */
    void handle(SeedRolesCommand command);
}
