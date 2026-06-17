package com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound service interface used by the Skin Analysis context to query the IAM context.
 *
 * <p>The infrastructure layer provides the concrete implementation, which delegates to
 * {@link com.bloomie.platform.iam.interfaces.acl.IamContextFacade}.
 * Only primitive types cross this boundary.</p>
 */
public interface ExternalIamService {

    /**
     * Returns the {@link PatientId} for the given user id if the user exists in IAM.
     *
     * @param patientId the IAM user id to check
     * @return an Optional containing the PatientId, or empty if not found
     */
    Optional<PatientId> fetchPatientById(Long patientId);
}
