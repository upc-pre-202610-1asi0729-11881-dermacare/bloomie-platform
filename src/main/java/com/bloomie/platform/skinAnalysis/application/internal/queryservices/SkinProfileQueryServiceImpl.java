package com.bloomie.platform.skinAnalysis.application.internal.queryservices;

import com.bloomie.platform.skinAnalysis.application.queryservices.SkinProfileQueryService;
import com.bloomie.platform.skinAnalysis.domain.model.aggregates.SkinProfile;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByIdQuery;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;
import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinAnalysis.domain.repositories.SkinProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link SkinProfile} aggregate.
 *
 * <p>Delegates directly to the {@link SkinProfileRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class SkinProfileQueryServiceImpl implements SkinProfileQueryService {

    private final SkinProfileRepository skinProfileRepository;

    public SkinProfileQueryServiceImpl(SkinProfileRepository skinProfileRepository) {
        this.skinProfileRepository = skinProfileRepository;
    }

    @Override
    public Optional<SkinProfile> handle(GetSkinProfileByIdQuery query) {
        return skinProfileRepository.findById(query.skin_profile_id());
    }

    @Override
    public Optional<SkinProfile> handle(GetSkinProfileByPatientIdQuery query) {
        return skinProfileRepository.findByPatientId(new PatientId(query.patient_id()));
    }
}