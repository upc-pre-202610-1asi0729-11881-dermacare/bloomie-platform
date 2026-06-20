package com.bloomie.platform.dermatologycare.application.internal.queryservices;

import com.bloomie.platform.dermatologycare.application.queryservices.AvailabilityQueryService;
import com.bloomie.platform.dermatologycare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetAvailabilityByDermatologistIdAndDayQuery;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetAvailabilityByDermatologistIdQuery;
import com.bloomie.platform.dermatologycare.domain.repositories.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityQueryServiceImpl implements AvailabilityQueryService {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityQueryServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Availability> handle(GetAvailabilityByDermatologistIdQuery query) {
        return availabilityRepository.findAllByDermatologistId(query.dermatologistId());
    }

    @Override
    public List<Availability> handle(GetAvailabilityByDermatologistIdAndDayQuery query) {
        return availabilityRepository.findAllByDermatologistAndDay(query.dermatologistId(), query.day());
    }
}
