package com.bloomie.platform.dermatologyCare.application.internal.queryservices;

import com.bloomie.platform.dermatologyCare.application.queryservices.DermatologistProfileQueryService;
import com.bloomie.platform.dermatologyCare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetAllDermatologistProfilesQuery;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetDermatologistProfileByDermatologistIdQuery;
import com.bloomie.platform.dermatologyCare.domain.model.queries.GetDermatologistProfileByIdQuery;
import com.bloomie.platform.dermatologyCare.domain.repositories.DermatologistProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that handles read operations on the {@link DermatologistProfile} aggregate.
 */
@Service
public class DermatologistProfileQueryServiceImpl implements DermatologistProfileQueryService {

    private final DermatologistProfileRepository dermatologistProfileRepository;

    public DermatologistProfileQueryServiceImpl(DermatologistProfileRepository dermatologistProfileRepository) {
        this.dermatologistProfileRepository = dermatologistProfileRepository;
    }

    @Override
    public Optional<DermatologistProfile> handle(GetDermatologistProfileByIdQuery query) {
        return dermatologistProfileRepository.findById(query.dermatologistProfileId());
    }

    @Override
    public Optional<DermatologistProfile> handle(GetDermatologistProfileByDermatologistIdQuery query) {
        return dermatologistProfileRepository.findByDermatologistId(query.dermatologistId());
    }

    @Override
    public List<DermatologistProfile> handle(GetAllDermatologistProfilesQuery query) {
        return dermatologistProfileRepository.findAll();
    }
}
