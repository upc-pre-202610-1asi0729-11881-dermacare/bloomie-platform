package com.bloomie.platform.skinAnalysis.infrastructure.acl;

import com.bloomie.platform.iam.interfaces.acl.IamContextFacade;
import com.bloomie.platform.skinAnalysis.application.internal.outboundservices.acl.ExternalIamService;
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
    public boolean existsUserById(Long user_id) {
        return iamContextFacade.existsUserById(user_id);
    }
}