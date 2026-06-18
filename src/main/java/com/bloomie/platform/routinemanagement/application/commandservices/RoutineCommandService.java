package com.bloomie.platform.routinemanagement.application.commandservices;

import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service contract for commands over the {@code Routine} aggregate.
 */
public interface RoutineCommandService {

    /**
     * Handles routine creation.
     *
     * @param command command containing initial routine data
     * @return created routine identifier or an application error
     */
    Result<Long, ApplicationError> handle(GeneratePersonalizedRoutineCommand command);
}