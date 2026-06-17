package com.bloomie.platform.skinanalysis.infrastructure.acl;

import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalSkinProfileService;
import com.bloomie.platform.skinanalysis.interfaces.acl.SkinProfileContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL implementation that wraps {@link SkinProfileContextFacade} to provide
 * skin profile lookups in terms of the FacialScan feature's requirements.
 */
@Service
public class ExternalSkinProfileServiceImpl implements ExternalSkinProfileService {

    private final SkinProfileContextFacade skinProfileContextFacade;

    public ExternalSkinProfileServiceImpl(SkinProfileContextFacade skinProfileContextFacade) {
        this.skinProfileContextFacade = skinProfileContextFacade;
    }

    /**
     * Returns the skin type name for the given patient, or empty if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return an Optional containing the skin type name, or empty if not found
     */
    @Override
    public Optional<String> fetchSkinTypeByPatientId(Long patientId) {
        var skinType = skinProfileContextFacade.fetchSkinTypeByPatientId(patientId);
        return skinType != null ? Optional.of(skinType) : Optional.empty();
    }

    /**
     * Returns the sensitivity name for the given patient, or empty if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return an Optional containing the sensitivity name, or empty if not found
     */
    @Override
    public Optional<String> fetchSensitivityByPatientId(Long patientId) {
        var sensitivity = skinProfileContextFacade.fetchSensitivityByPatientId(patientId);
        return sensitivity != null ? Optional.of(sensitivity) : Optional.empty();
    }
}
