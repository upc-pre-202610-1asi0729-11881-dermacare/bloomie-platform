package com.bloomie.platform.skinanalysis.application.queryservices;

import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysesByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByFacialScanIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service interface for all read operations on the
 * {@link SkinAnalysis} aggregate.
 */
public interface SkinAnalysisQueryService {

    /**
     * Returns the skin analysis with the given id, or empty if not found.
     *
     * @param query the query carrying the skin analysis id
     * @return an optional containing the skin analysis
     */
    Optional<SkinAnalysis> handle(GetSkinAnalysisByIdQuery query);

    /**
     * Returns the skin analysis associated with the given facial scan, or empty if none exists.
     *
     * @param query the query carrying the facial scan value object
     * @return an optional containing the skin analysis
     */
    Optional<SkinAnalysis> handle(GetSkinAnalysisByFacialScanIdQuery query);

    /**
     * Returns all skin analyses belonging to the given patient.
     *
     * @param query the query carrying the patient value object
     * @return a list of skin analyses (may be empty)
     */
    List<SkinAnalysis> handle(GetSkinAnalysesByPatientIdQuery query);
}
