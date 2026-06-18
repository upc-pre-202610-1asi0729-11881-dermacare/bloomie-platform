package com.bloomie.platform.routinemanagement.domain.model.valueobjects;

/**
 * Value object that holds a reference to a skin analysis from the Skin Analysis bounded context.
 *
 * @param skinAnalysisId the skin analysis id; must be a positive number
 */
public record SkinAnalysisId(Long skinAnalysisId) {

    private static final String INVALID_MESSAGE_KEY = "routine.skin.analysis.id.invalid";

    public SkinAnalysisId {
        if (skinAnalysisId == null || skinAnalysisId < 1) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}