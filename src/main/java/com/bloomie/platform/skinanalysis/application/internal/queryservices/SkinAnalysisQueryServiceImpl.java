package com.bloomie.platform.skinanalysis.application.internal.queryservices;

import com.bloomie.platform.skinanalysis.application.queryservices.SkinAnalysisQueryService;
import com.bloomie.platform.skinanalysis.domain.model.aggregates.SkinAnalysis;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysesByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByFacialScanIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinAnalysisByIdQuery;
import com.bloomie.platform.skinanalysis.domain.repositories.SkinAnalysisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link SkinAnalysis} aggregate.
 *
 * <p>Delegates directly to the {@link SkinAnalysisRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class SkinAnalysisQueryServiceImpl implements SkinAnalysisQueryService {

    private final SkinAnalysisRepository skinAnalysisRepository;

    public SkinAnalysisQueryServiceImpl(SkinAnalysisRepository skinAnalysisRepository) {
        this.skinAnalysisRepository = skinAnalysisRepository;
    }

    @Override
    public Optional<SkinAnalysis> handle(GetSkinAnalysisByIdQuery query) {
        return skinAnalysisRepository.findById(query.skinAnalysisId());
    }

    @Override
    public Optional<SkinAnalysis> handle(GetSkinAnalysisByFacialScanIdQuery query) {
        return skinAnalysisRepository.findByFacialScanId(query.facialScanId());
    }

    @Override
    public List<SkinAnalysis> handle(GetSkinAnalysesByPatientIdQuery query) {
        return skinAnalysisRepository.findAllByPatientId(query.patientId());
    }
}
