package com.bloomie.platform.dermatologicalAppointment.infrastructure.acl;

import com.bloomie.platform.dermatologicalAppointment.application.internal.outboundservices.acl.ExternalIamService;
import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL implementation that delegates to the IAM bounded context facade.
 * Only primitive types cross the boundary.
 */
@Service
public class ExternalIamServiceImpl implements ExternalIamService {

    private final IamContextFacade iamContextFacade;

    public ExternalIamServiceImpl(IamContextFacade iamContextFacade) {
        this.iamContextFacade = iamContextFacade;
    }

    @Override
    public boolean existsUserById(Long userId) {
        return iamContextFacade.existsUserById(userId);
    }
}
