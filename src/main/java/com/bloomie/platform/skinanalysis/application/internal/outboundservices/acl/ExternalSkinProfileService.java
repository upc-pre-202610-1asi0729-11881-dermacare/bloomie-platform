package com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl;

import java.util.Optional;

/**
 * Outbound service interface used by the FacialScan feature to query skin profile data.
 *
 * <p>The infrastructure layer provides the concrete implementation, which delegates to
 * {@link com.bloomie.platform.skinanalysis.interfaces.acl.SkinProfileContextFacade}.
 * Only primitive types cross this boundary.</p>
 */
public interface ExternalSkinProfileService {

    /**
     * Returns the skin type name for the given patient, or empty if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return an Optional containing the skin type name, or empty if not found
     */
    Optional<String> fetchSkinTypeByPatientId(Long patientId);

    /**
     * Returns the sensitivity name for the given patient, or empty if no profile exists.
     *
     * @param patientId the IAM user id of the patient
     * @return an Optional containing the sensitivity name, or empty if not found
     */
    Optional<String> fetchSensitivityByPatientId(Long patientId);
}
