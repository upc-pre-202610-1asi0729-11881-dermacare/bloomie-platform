package com.bloomie.platform.iam.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.iam.domain.model.aggregates.User;
import com.bloomie.platform.iam.domain.model.valueobjects.PersonName;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.bloomie.platform.iam.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Stateless assembler that converts between the {@link User} domain aggregate
 * and its JPA counterpart {@link UserPersistenceEntity}.
 *
 * <p>Private helper methods handle the nested {@link PersonName} ↔
 * {@link PersonNamePersistenceEmbeddable} translation so the public methods
 * stay readable.</p>
 */
public final class UserPersistenceAssembler {

    private UserPersistenceAssembler() {}

    /** Reconstructs a {@link User} domain aggregate from a stored {@link UserPersistenceEntity}. */
    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        var roles = entity.getRoles().stream()
                .map(RolePersistenceAssembler::toDomainFromPersistence)
                .collect(Collectors.toSet());
        return new User(
                entity.getId(),
                toDomainFromPersistence(entity.getName()),
                entity.getEmailAddress(),
                entity.getHashedPassword(),
                roles,
                entity.getPhotoUrl());
    }

    /** Converts a {@link User} domain aggregate to a {@link UserPersistenceEntity} ready to be saved. */
    public static UserPersistenceEntity toPersistenceFromDomain(User user) {
        var entity = new UserPersistenceEntity();
        entity.setId(user.getId());
        entity.setName(toPersistenceFromDomain(user.getName()));
        entity.setEmailAddress(user.getEmailAddressValue());
        entity.setHashedPassword(user.getHashedPassword());
        entity.setPhotoUrl(user.getPhotoUrl());
        var roleEntities = user.getRoles().stream()
                .map(RolePersistenceAssembler::toPersistenceFromDomain)
                .collect(Collectors.toSet());
        entity.setRoles(new HashSet<>(roleEntities));
        return entity;
    }

    private static PersonName toDomainFromPersistence(PersonNamePersistenceEmbeddable value) {
        return value == null ? null : new PersonName(value.getFirstName(), value.getLastName());
    }

    private static PersonNamePersistenceEmbeddable toPersistenceFromDomain(PersonName value) {
        return value == null ? null : new PersonNamePersistenceEmbeddable(value.firstName(), value.lastName());
    }
}