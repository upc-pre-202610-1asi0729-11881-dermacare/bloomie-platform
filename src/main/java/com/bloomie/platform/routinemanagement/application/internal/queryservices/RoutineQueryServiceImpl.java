package com.bloomie.platform.routinemanagement.application.internal.queryservices;

import com.bloomie.platform.routinemanagement.application.internal.outboundservices.ai.RoutineAiService;
import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRecommendedProductsForRoutineItemQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByPatientIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application service that resolves routine read queries.
 */
@Service
public class RoutineQueryServiceImpl implements RoutineQueryService {

    private final RoutineRepository routineRepository;
    private final RoutineAiService routineAiService;

    public RoutineQueryServiceImpl(RoutineRepository routineRepository, RoutineAiService routineAiService) {
        this.routineRepository = routineRepository;
        this.routineAiService = routineAiService;
    }

    @Override
    public Optional<Routine> handle(GetRoutineByIdQuery query) {
        return routineRepository.findById(query.routineId());
    }

    @Override
    public Optional<Routine> handle(GetRoutineByPatientIdQuery query) {
        return routineRepository.findActiveByPatientId(new PatientId(query.patientId()));
    }

    @Override
    public List<String> handle(GetRecommendedProductsForRoutineItemQuery query) {
        var routine = routineRepository.findById(query.routineId());
        if (routine.isEmpty()) return List.of();
        var item = routine.get().getItems().stream()
                .filter(i -> i.getId().equals(query.routineItemId()))
                .findFirst();
        if (item.isEmpty()) return List.of();

        return routineAiService.selectAlternativeProductsForStep(
                routine.get().getSkinType(),
                item.get().getStep(),
                item.get().getProductRecommendation());
    }
}
