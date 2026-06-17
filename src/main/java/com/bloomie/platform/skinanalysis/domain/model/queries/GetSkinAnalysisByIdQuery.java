package com.bloomie.platform.skinanalysis.domain.model.queries;

/**
 * Query to retrieve a skin analysis by its persistence id.
 *
 * @param skinAnalysisId the id of the skin analysis to retrieve
 */
public record GetSkinAnalysisByIdQuery(Long skinAnalysisId) {
}
