package com.bloomie.platform.intelligentsupport.application.internal.queryservices;

import com.bloomie.platform.intelligentsupport.application.queryservices.SupportQueryQueryService;
import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByIdQuery;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByPatientIdAndStatusQuery;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryStatus;
import com.bloomie.platform.intelligentsupport.domain.repositories.SupportQueryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service that handles all read operations on the {@link SupportQuery} aggregate.
 *
 * <p>Delegates directly to the {@link SupportQueryRepository} domain port without any
 * mutation or event publication.</p>
 */
@Service
public class SupportQueryQueryServiceImpl implements SupportQueryQueryService {

    private final SupportQueryRepository supportQueryRepository;

    /**
     * Creates a new instance with the required repository dependency.
     *
     * @param supportQueryRepository the domain repository port for support queries
     */
    public SupportQueryQueryServiceImpl(SupportQueryRepository supportQueryRepository) {
        this.supportQueryRepository = supportQueryRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SupportQuery> handle(GetSupportQueryByIdQuery query) {
        return supportQueryRepository.findById(query.id());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SupportQuery> handle(GetSupportQueryByPatientIdAndStatusQuery query) {
        return supportQueryRepository.findByPatientIdAndStatus(
                new PatientId(query.patientId()),
                SupportQueryStatus.valueOf(query.status()));
    }
}
