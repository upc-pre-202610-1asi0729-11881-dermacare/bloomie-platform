package com.bloomie.platform.dermatologicalAppointment.infrastructure.acl;

import com.bloomie.platform.dermatologicalAppointment.application.internal.outboundservices.acl.ExternalDermatologyCareService;
import com.bloomie.platform.dermatologyCare.interfaces.acl.DermatologyCareContextFacade;
import org.springframework.stereotype.Service;

/**
 * Outbound ACL implementation that delegates to the Dermatology Care bounded context facade.
 * Only primitive types cross the boundary.
 */
@Service
public class ExternalDermatologyCareServiceImpl implements ExternalDermatologyCareService {

    private final DermatologyCareContextFacade dermatologyCareContextFacade;

    public ExternalDermatologyCareServiceImpl(DermatologyCareContextFacade dermatologyCareContextFacade) {
        this.dermatologyCareContextFacade = dermatologyCareContextFacade;
    }

    @Override
    public boolean existsDermatologistProfile(Long dermatologistId) {
        return dermatologyCareContextFacade.existsDermatologistProfileByDermatologistId(dermatologistId);
    }
}
