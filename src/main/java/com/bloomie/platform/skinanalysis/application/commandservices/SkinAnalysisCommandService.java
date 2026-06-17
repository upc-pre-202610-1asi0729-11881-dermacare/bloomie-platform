package com.bloomie.platform.skinanalysis.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.domain.model.commands.AnalyzeSkinScanCommand;

/**
 * Application service interface for all write operations on the
 * {@link com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis} aggregate.
 */
public interface SkinAnalysisCommandService {

    /**
     * Processes the given facial scan and produces a skin analysis with computed scores.
     *
     * @param command the command carrying the facial scan id, patient id, skin type and sensitivity
     * @return the id of the newly created skin analysis, or an error
     */
    Result<Long, ApplicationError> handle(AnalyzeSkinScanCommand command);
}
