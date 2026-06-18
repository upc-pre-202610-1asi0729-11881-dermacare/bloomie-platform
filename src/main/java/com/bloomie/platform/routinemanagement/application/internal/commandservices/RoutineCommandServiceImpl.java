package com.bloomie.platform.routinemanagement.application.internal.commandservices;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
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

    public RoutineCommandServiceImpl(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @Override
    public Result<Long, ApplicationError> handle(GeneratePersonalizedRoutineCommand command) {
        var routine = new Routine(command);
        try {
            routine = routineRepository.save(routine);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("create-routine", e.getMessage()));
        }
        return Result.success(routine.getId());
    }
}