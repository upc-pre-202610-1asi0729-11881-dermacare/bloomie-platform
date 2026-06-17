package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.entities.SkinAnalysisPersistenceEntity;

/**
 * Stateless assembler that converts between the {@link SkinAnalysis} domain aggregate
 * and its JPA counterpart {@link SkinAnalysisPersistenceEntity}.
 */
public final class SkinAnalysisPersistenceAssembler {

    private SkinAnalysisPersistenceAssembler() {}

    /** Reconstructs a {@link SkinAnalysis} aggregate from a stored entity. */
    public static SkinAnalysis toDomainFromPersistence(SkinAnalysisPersistenceEntity entity) {
        return new SkinAnalysis(
                entity.getId(),
                entity.getPatientId(),
                entity.getFacialScanId(),
                entity.getOverallScore(),
                entity.getHydrationScore(),
                entity.getTextureScore(),
                entity.getSensitivityScore(),
                entity.getBrightnessScore(),
                entity.getStatus(),
                entity.getAnalyzedAt());
    }

    /** Converts a {@link SkinAnalysis} aggregate to a persistence entity ready to save. */
    public static SkinAnalysisPersistenceEntity toPersistenceFromDomain(SkinAnalysis skinAnalysis) {
        var entity = new SkinAnalysisPersistenceEntity();
        entity.setId(skinAnalysis.getId());
        entity.setPatientId(skinAnalysis.getPatientIdValue());
        entity.setFacialScanId(skinAnalysis.getFacialScanIdValue());
        entity.setOverallScore(skinAnalysis.getOverallScore());
        entity.setHydrationScore(skinAnalysis.getHydrationScore());
        entity.setTextureScore(skinAnalysis.getTextureScore());
        entity.setSensitivityScore(skinAnalysis.getSensitivityScore());
        entity.setBrightnessScore(skinAnalysis.getBrightnessScore());
        entity.setStatus(skinAnalysis.getStatus());
        entity.setAnalyzedAt(skinAnalysis.getAnalyzedAt());
        return entity;
    }
}
