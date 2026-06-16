package com.bloomie.platform.dermatologyCare.application.acl;

import com.bloomie.platform.dermatologyCare.application.queryservices.DermatologistProfileQueryService;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetDermatologistProfileByDermatologistIdQuery;
import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.DermatologistId;
import com.bloomie.platform.dermatologyCare.interfaces.acl.DermatologyCareContextFacade;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link DermatologyCareContextFacade}.
 *
 * <p>Delegates to the query service using the internal domain model, then exposes
 * only primitive values so that no domain types leak across the ACL boundary.</p>
 */
@Service
public class DermatologyCareContextFacadeImpl implements DermatologyCareContextFacade {

    private final DermatologistProfileQueryService profileQueryService;

    public DermatologyCareContextFacadeImpl(DermatologistProfileQueryService profileQueryService) {
        this.profileQueryService = profileQueryService;
    }

    @Override
    public boolean existsDermatologistProfileByDermatologistId(Long dermatologistId) {
        var query = new GetDermatologistProfileByDermatologistIdQuery(new DermatologistId(dermatologistId));
        return profileQueryService.handle(query).isPresent();
    }

    @Override
    public Long fetchProfileIdByDermatologistId(Long dermatologistId) {
        var query = new GetDermatologistProfileByDermatologistIdQuery(new DermatologistId(dermatologistId));
        return profileQueryService.handle(query)
                .map(p -> p.getId())
                .orElse(0L);
    }
}
