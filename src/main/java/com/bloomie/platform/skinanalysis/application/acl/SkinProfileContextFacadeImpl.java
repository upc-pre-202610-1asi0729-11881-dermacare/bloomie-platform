package com.bloomie.platform.skinanalysis.application.acl;

import com.bloomie.platform.skinanalysis.application.queryservices.SkinProfileQueryService;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.interfaces.acl.SkinProfileContextFacade;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link SkinProfileContextFacade} that delegates to the internal
 * {@link SkinProfileQueryService} and exposes only primitive values to consumers.
 */
@Service
public class SkinProfileContextFacadeImpl implements SkinProfileContextFacade {

    private final SkinProfileQueryService skinProfileQueryService;

    public SkinProfileContextFacadeImpl(SkinProfileQueryService skinProfileQueryService) {
        this.skinProfileQueryService = skinProfileQueryService;
    }

    /**
     * Returns the skin type name for the patient, or {@code null} if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return the skin type name, or {@code null}
     */
    @Override
    public String fetchSkinTypeByPatientId(Long patientId) {
        return skinProfileQueryService
                .handle(new GetSkinProfileByPatientIdQuery(new PatientId(patientId)))
                .map(profile -> profile.getSkinType().name())
                .orElse(null);
    }

    /**
     * Returns the sensitivity name for the patient, or {@code null} if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return the sensitivity name, or {@code null}
     */
    @Override
    public String fetchSensitivityByPatientId(Long patientId) {
        return skinProfileQueryService
                .handle(new GetSkinProfileByPatientIdQuery(new PatientId(patientId)))
                .map(profile -> profile.getSensitivity().name())
                .orElse(null);
    }
}
