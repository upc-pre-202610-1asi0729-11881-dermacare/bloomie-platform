package com.bloomie.platform.intelligentsupport.application.internal.queryservices;

import com.bloomie.platform.intelligentsupport.application.queryservices.SupportQueryQueryService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByIdQuery;
import com.bloomie.platform.intelligentsupport.domain.repositories.SupportQueryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupportQueryQueryServiceImpl implements SupportQueryQueryService {
    private final SupportQueryRepository supportQueryRepository;

    public SupportQueryQueryServiceImpl(SupportQueryRepository supportQueryRepository) {
        this.supportQueryRepository = supportQueryRepository;
    }

    @Override
    public Optional<SupportQuery> handle(GetSupportQueryByIdQuery query) {
        return supportQueryRepository.findById(query.id());
    }
}
