package com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl;

import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * ACL service used by the Skin Analysis bounded context to interact with IAM capabilities.
 *
 * <p>Wraps {@link IamContextFacade} so that internal services never depend directly on
 * IAM domain or application types. Only primitive types cross this boundary.</p>
 */
@Service
public class ExternalIamService {

    private final IamContextFacade iamContextFacade;

    /**
     * Creates the service with the IAM ACL facade dependency.
     *
     * @param iamContextFacade IAM bounded-context facade
     */
    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Returns the {@link PatientId} for the given user id if the user exists in IAM.
     *
     * @param patientId the IAM user id to check
     * @return an Optional containing the PatientId, or empty if not found
     */
    public Optional<PatientId> fetchPatientById(Long patientId) {
        return iamContextFacade.existsUserById(patientId)
                ? Optional.of(new PatientId(patientId))
                : Optional.empty();
    }
}
