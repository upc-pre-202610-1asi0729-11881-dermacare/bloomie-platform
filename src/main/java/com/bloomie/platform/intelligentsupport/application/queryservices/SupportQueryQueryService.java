package com.bloomie.platform.intelligentsupport.application.queryservices;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByIdQuery;

import java.util.Optional;

public interface SupportQueryQueryService {
    Optional<SupportQuery> handle(GetSupportQueryByIdQuery query);
}
