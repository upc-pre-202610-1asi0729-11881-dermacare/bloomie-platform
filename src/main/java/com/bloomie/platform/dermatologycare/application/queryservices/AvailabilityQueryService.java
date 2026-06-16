package com.bloomie.platform.dermatologyCare.application.queryservices;

import com.bloomie.platform.dermatologyCare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetAvailabilityByDermatologistIdAndDayQuery;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetAvailabilityByDermatologistIdQuery;

import java.util.List;

/**
 * Application service port for read operations on the {@link Availability} aggregate.
 */
public interface AvailabilityQueryService {
    List<Availability> handle(GetAvailabilityByDermatologistIdQuery query);
    List<Availability> handle(GetAvailabilityByDermatologistIdAndDayQuery query);
}
