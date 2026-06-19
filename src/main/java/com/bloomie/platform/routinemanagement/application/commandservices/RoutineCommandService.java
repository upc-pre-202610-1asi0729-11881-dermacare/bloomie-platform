package com.bloomie.platform.routinemanagement.application.commandservices;

import com.bloomie.platform.routinemanagement.domain.model.aggregates.Routine;
import com.bloomie.platform.routinemanagement.domain.model.commands.GeneratePersonalizedRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.RemoveProductFromRoutineCommand;
import com.bloomie.platform.routinemanagement.domain.model.commands.ReplaceProductInRoutineCommand;
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
     * @see GeneratePersonalizedRoutineCommand
     */
    Result<Long, ApplicationError> handle(GeneratePersonalizedRoutineCommand command);

    /**
     * Handles product replacement in a routine item.
     *
     * @param command command containing the routine id, item id and new product name
     * @return updated routine aggregate or an application error
     * @see ReplaceProductInRoutineCommand
     */
    Result<Routine, ApplicationError> handle(ReplaceProductInRoutineCommand command);

    /**
     * Handles removal of an optional product step from a routine.
     *
     * @param command command containing the routine and item identifiers
     * @return updated routine aggregate or an application error
     * @see RemoveProductFromRoutineCommand
     */
    Result<Routine, ApplicationError> handle(RemoveProductFromRoutineCommand command);

}