package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.SkinProfilePersistenceEntity;

/**
 * Stateless assembler that converts between the {@link SkinProfile} domain aggregate
 * and its JPA counterpart {@link SkinProfilePersistenceEntity}.
 */
public final class SkinProfilePersistenceAssembler {

    private SkinProfilePersistenceAssembler() {}

    /** Reconstructs a {@link SkinProfile} aggregate from a stored entity. */
    public static SkinProfile toDomainFromPersistence(SkinProfilePersistenceEntity entity) {
        return new SkinProfile(
                entity.getId(),
                entity.getPatientId(),
                entity.getSkinType(),
                entity.getSensitivity(),
                entity.getWaterIntake(),
                entity.getSunExposure(),
                entity.getSleepHours(),
                entity.getStatus());
    }

    /** Converts a {@link SkinProfile} aggregate to a persistence entity ready to save. */
    public static SkinProfilePersistenceEntity toPersistenceFromDomain(SkinProfile skinProfile) {
        var entity = new SkinProfilePersistenceEntity();
        entity.setId(skinProfile.getId());
        entity.setPatientId(skinProfile.getPatientId());
        entity.setSkinType(skinProfile.getSkinType());
        entity.setSensitivity(skinProfile.getSensitivity());
        entity.setWaterIntake(skinProfile.getWaterIntake());
        entity.setSunExposure(skinProfile.getSunExposure());
        entity.setSleepHours(skinProfile.getSleepHours());
        entity.setStatus(skinProfile.getStatus());
        return entity;
    }
}
