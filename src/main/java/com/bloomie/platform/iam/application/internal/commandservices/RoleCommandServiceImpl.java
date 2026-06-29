package com.bloomie.platform.iam.application.internal.commandservices;

import com.bloomie.platform.iam.application.commandservices.RoleCommandService;
import com.bloomie.platform.iam.domain.model.commands.SeedRolesCommand;
import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;
import com.bloomie.platform.iam.domain.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation of {@link RoleCommandService} that handles {@link SeedRolesCommand}.
 *
 * <p>Iterates over every {@link UserRole} enum value and persists a matching
 * {@link Role} only if it does not already exist, making the seed operation idempotent.</p>
 */
@Service
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Ensures every {@link UserRole} value has a corresponding row in the roles table.
     * Skips roles that are already present.
     *
     * @param command the seed-roles command (carries no data — acts as a trigger)
     */
    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(UserRole.values()).forEach(userRole -> {
            if (!roleRepository.existsByName(userRole)) {
                roleRepository.save(new Role(userRole));
            }
        });
    }
}
