package com.bloomie.platform.skinanalysis.interfaces.rest.transform;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.SkinAnalysisResource;

/**
 * Converts a {@link SkinAnalysis} aggregate into a {@link SkinAnalysisResource} response.
 */
public final class SkinAnalysisResourceFromEntityAssembler {

    private SkinAnalysisResourceFromEntityAssembler() {}

    public static SkinAnalysisResource toResourceFromEntity(SkinAnalysis skinAnalysis) {
        return new SkinAnalysisResource(
                skinAnalysis.getId(),
                skinAnalysis.getPatientId(),
                skinAnalysis.getFacialScanId(),
                skinAnalysis.getOverallScore(),
                skinAnalysis.getHydrationScore(),
                skinAnalysis.getTextureScore(),
                skinAnalysis.getSensitivityScore(),
                skinAnalysis.getBrightnessScore(),
                skinAnalysis.getStatus().name(),
                skinAnalysis.getAnalyzedAt() != null ? skinAnalysis.getAnalyzedAt().toString() : null);
    }
}
