package com.bloomie.platform.subscription.application.internal.outboundservices.acl;

import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExternalIamService {
    private final IamContextFacade iamContextFacade;

    public ExternalIamService(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    public Optional<PatientId> fetchPatientById(Long userId) {
        var existes = iamContextFacade.fetchUserByEmail(userId.toString());
        return userId == 0L ? Optional.empty() : Optional.of(new PatientId(userId));
    }
}