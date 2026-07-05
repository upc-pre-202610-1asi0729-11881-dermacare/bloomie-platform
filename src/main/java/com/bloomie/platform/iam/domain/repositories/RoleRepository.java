package com.bloomie.platform.iam.domain.repositories;

import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.domain.model.valueobjects.UserRole;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository port for the {@link Role} entity.
 *
 * <p>Separates role persistence from user persistence following the
 * single-responsibility principle. The infrastructure layer provides
 * the adapter via {@link com.bloomie.platform.iam.infrastructure.persistence.jpa.adapters.RoleRepositoryImpl}.</p>
 */
public interface RoleRepository {

    /** Finds a role by its enum name. */
    Optional<Role> findByName(UserRole name);

    /** Returns all roles stored in the system. */
    List<Role> findAll();

    /** Persists a new role. */
    Role save(Role role);

    /** Returns {@code true} if a role with the given enum name already exists. */
    boolean existsByName(UserRole name);
}
