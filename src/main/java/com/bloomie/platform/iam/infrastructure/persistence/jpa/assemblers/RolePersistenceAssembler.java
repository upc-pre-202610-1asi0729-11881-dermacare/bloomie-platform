package com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.iam.domain.model.entities.Role;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;

/**
 * Stateless assembler that converts between the {@link Role} domain entity
 * and its JPA counterpart {@link RolePersistenceEntity}.
 */
public final class RolePersistenceAssembler {

    private RolePersistenceAssembler() {}

    /** Maps a JPA persistence entity to a domain {@link Role}. */
    public static Role toDomainFromPersistence(RolePersistenceEntity entity) {
        return new Role(entity.getId(), entity.getName());
    }

    /** Maps a domain {@link Role} to a JPA persistence entity. */
    public static RolePersistenceEntity toPersistenceFromDomain(Role role) {
        var entity = new RolePersistenceEntity();
        entity.setId(role.getId());
        entity.setName(role.getName());
        return entity;
    }
}