package com.bloomie.platform.dermatologyCare.application.queryservices;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetAllDermatologistProfilesQuery;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetDermatologistProfileByDermatologistIdQuery;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetDermatologistProfileByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service port for read operations on the {@link DermatologistProfile} aggregate.
 */
public interface DermatologistProfileQueryService {
    Optional<DermatologistProfile> handle(GetDermatologistProfileByIdQuery query);
    Optional<DermatologistProfile> handle(GetDermatologistProfileByDermatologistIdQuery query);
    List<DermatologistProfile> handle(GetAllDermatologistProfilesQuery query);
}
