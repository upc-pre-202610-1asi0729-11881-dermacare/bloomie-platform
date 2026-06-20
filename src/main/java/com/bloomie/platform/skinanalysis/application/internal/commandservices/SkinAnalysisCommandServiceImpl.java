package com.bloomie.platform.skinanalysis.application.internal.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.skinanalysis.application.commandservices.SkinAnalysisCommandService;
import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.domain.model.commands.AnalyzeSkinScanCommand;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import com.bloomie.platform.skinanalysis.domain.repositories.SkinAnalysisRepository;
import org.springframework.stereotype.Service;

/**
 * Application service that handles write operations on the {@link SkinAnalysis} aggregate.
 *
 * <p>Domain events are published by the repository adapter after each save.</p>
 */
@Service
public class SkinAnalysisCommandServiceImpl implements SkinAnalysisCommandService {

    private static final String ANALYSIS_ALREADY_EXISTS  = "skin.analysis.already.exists";
    private static final String FACIAL_SCAN_ID_INVALID   = "skin.analysis.facial.scan.id.invalid";

    private final SkinAnalysisRepository skinAnalysisRepository;

    public SkinAnalysisCommandServiceImpl(SkinAnalysisRepository skinAnalysisRepository) {
        this.skinAnalysisRepository = skinAnalysisRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(AnalyzeSkinScanCommand command) {
        if (command.facialScanId() == null || command.facialScanId() < 1)
            return Result.failure(ApplicationError.validationError("facial-scan-id", FACIAL_SCAN_ID_INVALID));

        if (skinAnalysisRepository.findByFacialScanId(new FacialScanId(command.facialScanId())).isPresent())
            return Result.failure(ApplicationError.conflict("skin-analysis", ANALYSIS_ALREADY_EXISTS));

        var saved = skinAnalysisRepository.save(new SkinAnalysis(command));
        return Result.success(saved.getId());
    }
}
