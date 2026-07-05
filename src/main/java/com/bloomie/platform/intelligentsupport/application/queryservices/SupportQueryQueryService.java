package com.bloomie.platform.intelligentsupport.application.queryservices;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByIdQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByPatientIdAndStatusQuery;

import java.util.Optional;

/**
 * Application service interface for all read operations on the {@link SupportQuery} aggregate.
 */
public interface SupportQueryQueryService {

    /**
     * Returns the support query with the given id, or empty if not found.
     *
     * @param query the query carrying the support query id
     * @return an optional containing the support query
     */
    Optional<SupportQuery> handle(GetSupportQueryByIdQuery query);

    /**
     * Returns the support query belonging to the given patient that matches the given status,
     * or empty if no such session exists.
     *
     * @param query the query carrying the patient id and the status name
     * @return an optional containing the matching support query
     */
    Optional<SupportQuery> handle(GetSupportQueryByPatientIdAndStatusQuery query);
}
