package com.bloomie.platform.routinemanagement.application.commandservices;

import com.bloomie.platform.routinemanagement.domain.model.commands.MarkRoutineAsCompletedCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service contract for commands over the {@link com.bloomie.platform.routinemanagement.domain.model.aggregates.DailyTracking} aggregate.
 */
public interface DailyTrackingCommandService {

    /**
     * Handles marking a routine as completed for a given date.
     *
     * @param command command containing the patient id, routine id and completion date
     * @return the identifier of the created daily tracking entry, or an application error
     * @see MarkRoutineAsCompletedCommand
     */
    Result<Long, ApplicationError> handle(MarkRoutineAsCompletedCommand command);
}
