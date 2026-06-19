package com.bloomie.platform.routinemanagement.application.internal.commandservices;

import com.bloomie.platform.routinemanagement.application.commandservices.DailyTrackingCommandService;
import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking;
import com.bloomie.platform.routinemanagement.domain.model.commands.MarkRoutineAsCompletedCommand;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetRoutineByIdQuery;
import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import com.bloomie.platform.routinemanagement.domain.repositories.DailyTrackingRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Application service that executes daily tracking commands.
 */
@Service
public class DailyTrackingCommandServiceImpl implements DailyTrackingCommandService {

    private final DailyTrackingRepository dailyTrackingRepository;
    private final RoutineQueryService routineQueryService;

    public DailyTrackingCommandServiceImpl(DailyTrackingRepository dailyTrackingRepository,
                                           RoutineQueryService routineQueryService) {
        this.dailyTrackingRepository = dailyTrackingRepository;
        this.routineQueryService = routineQueryService;
    }

    @Override
    public Result<Long, ApplicationError> handle(MarkRoutineAsCompletedCommand command) {
        if (command.date().isAfter(LocalDate.now()))
            return Result.failure(ApplicationError.businessRuleViolation(
                    "mark-routine-completed", "daily.tracking.future.date"));

        var patientId = new PatientId(command.patientId());
        if (dailyTrackingRepository.existsByPatientIdAndDate(patientId, command.date()))
            return Result.failure(ApplicationError.conflict(
                    "DailyTracking", "daily.tracking.already.completed"));

        var routineQuery = new GetRoutineByIdQuery(command.routineId());
        if (routineQueryService.handle(routineQuery).isEmpty())
            return Result.failure(ApplicationError.notFound("Routine",
                    command.routineId().toString()));

        try {
            var tracking = new DailyTracking(command);
            var saved = dailyTrackingRepository.save(tracking);
            return Result.success(saved.getId());
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "mark-routine-completed", e.getMessage()));
        }
    }
}
