package com.bloomie.platform.routinemanagement.application.internal.commandservices;

import com.bloomie.platform.routinemanagement.application.commandservices.RoutineCommandService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
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

    public RoutineCommandServiceImpl(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
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
}