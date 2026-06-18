package com.bloomie.platform.routinemanagement.infrastructure.acl;

import com.bloomie.platform.routinemanagement.application.internal.outboundservices.acl.ExternalSkinAnalysisService;
import com.bloomie.platform.skinanalysis.interfaces.acl.SkinProfileContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL implementation that wraps {@link SkinProfileContextFacade} to provide
 * skin type lookups for the Routine Management context.
 */
@Service
public class ExternalSkinAnalysisServiceImpl implements ExternalSkinAnalysisService {

    private final SkinProfileContextFacade skinProfileContextFacade;

    public ExternalSkinAnalysisServiceImpl(SkinProfileContextFacade skinProfileContextFacade) {
        this.skinProfileContextFacade = skinProfileContextFacade;
    }

    @Override
    public Optional<String> fetchSkinTypeByPatientId(Long patientId) {
        var skinType = skinProfileContextFacade.fetchSkinTypeByPatientId(patientId);
        return skinType != null ? Optional.of(skinType) : Optional.empty();
    }
}