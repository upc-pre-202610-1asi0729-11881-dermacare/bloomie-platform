package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.PersonName;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.embeddables.PersonNamePersistenceEmbeddable;
import com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.entities.DermatologistProfilePersistenceEntity;

/**
 * Stateless assembler that converts between the {@link DermatologistProfile} domain aggregate
 * and its JPA counterpart {@link DermatologistProfilePersistenceEntity}.
 */
public final class DermatologistProfilePersistenceAssembler {

    private DermatologistProfilePersistenceAssembler() {}

    /** Reconstructs a {@link DermatologistProfile} aggregate from a stored entity. */
    public static DermatologistProfile toDomainFromPersistence(DermatologistProfilePersistenceEntity entity) {
        return new DermatologistProfile(
                entity.getId(),
                entity.getDermatologistId(),
                toDomainFromPersistence(entity.getName()),
                entity.getSpecialty(),
                entity.getLicenseNumber(),
                entity.getContactPhone(),
                entity.getBiography());
    }

    /** Converts a {@link DermatologistProfile} aggregate to a persistence entity ready to save. */
    public static DermatologistProfilePersistenceEntity toPersistenceFromDomain(DermatologistProfile profile) {
        var entity = new DermatologistProfilePersistenceEntity();
        entity.setId(profile.getId());
        entity.setDermatologistId(profile.getDermatologistIdValue());
        entity.setName(toPersistenceFromDomain(profile.getName()));
        entity.setSpecialty(profile.getSpecialty());
        entity.setLicenseNumber(profile.getLicenseNumber());
        entity.setContactPhone(profile.getContactPhone());
        entity.setBiography(profile.getBiography());
        return entity;
    }

    private static PersonName toDomainFromPersistence(PersonNamePersistenceEmbeddable value) {
        return value == null ? null : new PersonName(value.getFirstName(), value.getLastName());
    }

    private static PersonNamePersistenceEmbeddable toPersistenceFromDomain(PersonName value) {
        return value == null ? null : new PersonNamePersistenceEmbeddable(value.firstName(), value.lastName());
    }
}
