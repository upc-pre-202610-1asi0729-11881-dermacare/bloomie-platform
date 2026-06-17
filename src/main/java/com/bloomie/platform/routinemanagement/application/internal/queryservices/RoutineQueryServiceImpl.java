package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutinesQuery;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Routine> handle(GetAllRoutinesQuery query) {
        return routineRepository.findAll();
    }
}