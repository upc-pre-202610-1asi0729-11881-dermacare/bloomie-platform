package com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.SkinConcerns;
import com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.entities.SkinProfilePersistenceEntity;

/**
 * Static utility that converts between {@link SkinProfile} domain aggregates and
 * {@link SkinProfilePersistenceEntity} JPA entities.
 */
public final class SkinProfilePersistenceAssembler {

    private SkinProfilePersistenceAssembler() {}

    public static SkinProfile toDomainFromPersistence(SkinProfilePersistenceEntity entity) {
        return new SkinProfile(
                entity.getId(),
                entity.getPatientId(),
                entity.getSkinType(),
                entity.getSkinTone(),
                new SkinConcerns(entity.getConcerns()),
                entity.getStatus());
    }

    public static SkinProfilePersistenceEntity toPersistenceFromDomain(SkinProfile skinProfile) {
        var entity = new SkinProfilePersistenceEntity();
        if (skinProfile.getId() != null) {
            entity.setId(skinProfile.getId());
        }
        entity.setPatientId(skinProfile.getPatientId());
        entity.setSkinType(skinProfile.getSkinType());
        entity.setSkinTone(skinProfile.getSkinTone());
        entity.setConcerns(skinProfile.getSkinConcerns().concerns());
        entity.setStatus(skinProfile.getStatus());
        return entity;
    }
}
