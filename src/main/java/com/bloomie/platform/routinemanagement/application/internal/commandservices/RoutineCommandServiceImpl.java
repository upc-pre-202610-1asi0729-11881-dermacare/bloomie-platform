package com.bloomie.platform.routinemanagement.application.internal.commandservices;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.application.internal.outboundservices.ai.RoutineAiService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.RemoveProductFromRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.ReplaceProductInRoutineCommand;
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

    private final RoutineAiService routineAiService;

    public RoutineCommandServiceImpl(RoutineRepository routineRepository, RoutineAiService routineAiService) {
        this.routineRepository = routineRepository;
        this.routineAiService = routineAiService;
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

            var stepNames = routine.getItems().stream()
                    .map(item -> item.getStep())
                    .toList();
            var aiProducts = routineAiService.selectProductsForRoutine(command.skinType(), stepNames);
            routine.getItems().forEach(item -> {
                var aiProduct = aiProducts.get(item.getStep());
                if (aiProduct != null) {
                    item.updateProductRecommendation(aiProduct);
                }
            });

            routine = routineRepository.save(routine);
            return Result.success(routine.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("generate-routine", e.getMessage()));
        }
    }

    @Override
    public Result<Routine, ApplicationError> handle(RemoveProductFromRoutineCommand command) {
        var routine = routineRepository.findById(command.routineId());
        if (routine.isEmpty())
            return Result.failure(ApplicationError.notFound("Routine",
                    command.routineId().toString()));

        try {
            routine.get().removeItem(command);
            var saved = routineRepository.save(routine.get());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "remove-product", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "remove-product", e.getMessage()));
        }
    }

    @Override
    public Result<Routine, ApplicationError> handle(ReplaceProductInRoutineCommand command) {
        var routine = routineRepository.findById(command.routineId());
        if (routine.isEmpty())
            return Result.failure(ApplicationError.notFound("Routine",
                    command.routineId().toString()));

        var item = routine.get().getItems().stream()
                .filter(i -> i.getId().equals(command.routineItemId()))
                .findFirst();
        if (item.isEmpty())
            return Result.failure(ApplicationError.notFound("RoutineItem",
                    command.routineItemId().toString()));

        // Validated against the full product catalog for this step rather than a fresh AI
        // call: the AI-suggested alternatives shown to the patient can vary between calls,
        // but every catalog product for the step is always a legitimate replacement.
        var validOptions = routineAiService.getCatalogProductsForStep(item.get().getStep());
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