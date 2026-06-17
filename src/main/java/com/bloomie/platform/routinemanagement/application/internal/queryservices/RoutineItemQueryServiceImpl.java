package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineItemQueryService;
import com.bloomie.platform.routinemanagement.domain.model.entities.RoutineItem;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutineItemsQuery;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that resolves routine item read queries.
 */
@Service
public class RoutineItemQueryServiceImpl implements RoutineItemQueryService {

    private final RoutineItemRepository routineItemRepository;

    public RoutineItemQueryServiceImpl(RoutineItemRepository routineItemRepository) {
        this.routineItemRepository = routineItemRepository;
    }

    @Override
    public List<RoutineItem> handle(GetAllRoutineItemsQuery query) {
        return routineItemRepository.findAll();
    }
}