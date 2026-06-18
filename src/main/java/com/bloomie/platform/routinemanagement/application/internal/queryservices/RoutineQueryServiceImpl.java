package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Application service that resolves routine read queries.
 */
@Service
public class RoutineQueryServiceImpl implements RoutineQueryService {

    private final RoutineRepository routineRepository;

    public RoutineQueryServiceImpl(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @Override
    public Optional<Routine> handle(GetRoutineByIdQuery query) {
        return routineRepository.findById(query.routineId());
    }

    @Override
    public Optional<Routine> handle(GetRoutineByPatientIdQuery query) {
        return routineRepository.findActiveByPatientId(new PatientId(query.patientId()));
    }
}