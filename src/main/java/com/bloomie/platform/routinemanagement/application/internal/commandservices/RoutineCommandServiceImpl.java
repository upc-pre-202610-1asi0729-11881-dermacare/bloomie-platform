package com.bloomie.platform.routinemanagement.application.internal.commandservices;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.ReplaceProductInRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRecommendedProductsForRoutineItemQuery;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineStatus;
import com.bloomie.platform.routinemanagement.domain.repositories.RoutineRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes routine commands.
 */
@Service
public class RoutineCommandServiceImpl implements RoutineCommandService {

    private final RoutineRepository routineRepository;

    private final RoutineQueryService routineQueryService;

    public RoutineCommandServiceImpl(RoutineRepository routineRepository, RoutineQueryService routineQueryService) {
        this.routineRepository = routineRepository;
        this.routineQueryService = routineQueryService;
    }

    @Override
    public Result<Long, ApplicationError> handle(GeneratePersonalizedRoutineCommand command) {
        try {
            var patientId = new PatientId(command.patientId());

            routineRepository.findActiveByPatientId(patientId).ifPresent(existing -> {
                existing.setStatus(RoutineStatus.INACTIVE);
                routineRepository.save(existing);
            });

            var routine = new Routine(command);
            routine = routineRepository.save(routine);
            return Result.success(routine.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("generate-routine", e.getMessage()));
        }
    }

    @Override
    public Result<Routine, ApplicationError> handle(ReplaceProductInRoutineCommand command) {
        var routine = routineRepository.findById(command.routineId());
        if (routine.isEmpty())
            return Result.failure(ApplicationError.notFound("Routine",
                    command.routineId().toString()));

        var validOptions = routineQueryService.handle(
                new GetRecommendedProductsForRoutineItemQuery(
                        command.routineId(),
                        command.routineItemId()));

        if (!validOptions.contains(command.newProductRecommendation()))
            return Result.failure(ApplicationError.businessRuleViolation(
                    "replace-product",
                    "routine.product.not.recommended"));

        try {
            routine.get().replaceProduct(command);
            var saved = routineRepository.save(routine.get());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "replace-product", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "replace-product", e.getMessage()));
        }
    }
}