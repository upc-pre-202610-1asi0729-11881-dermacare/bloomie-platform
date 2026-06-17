package com.bloomie.platform.skinanalysis.infrastructure.acl;

import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import com.bloomie.platform.skinanalysis.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Outbound ACL implementation that wraps {@link IamContextFacade} to provide
 * IAM lookups in terms of Skin Analysis's own value objects.
 */
@Service("skinAnalysisExternalIamService")
public class ExternalIamServiceImpl implements ExternalIamService {

    private final IamContextFacade iamContextFacade;

    public ExternalIamServiceImpl(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    /**
     * Returns the {@link PatientId} for the given user id if the user exists in IAM.
     *
     * @param patientId the IAM user id
     * @return an Optional containing the PatientId, or empty if not found
     */
    @Override
    public Optional<PatientId> fetchPatientById(Long patientId) {
        return iamContextFacade.existsUserById(patientId)
                ? Optional.of(new PatientId(patientId))
                : Optional.empty();
    }
}
