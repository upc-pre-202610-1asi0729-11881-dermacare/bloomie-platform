package com.bloomie.platform.dermatologycare.application.commandservices;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.Availability;
import com.bloomie.platform.dermatologycare.domain.model.commands.DefineAvailabilityCommand;
import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateAvailabilityCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service port for write operations on the {@link Availability} aggregate.
 */
public interface AvailabilityCommandService {
    Result<Long, ApplicationError> handle(DefineAvailabilityCommand command);
    Result<Availability, ApplicationError> handle(UpdateAvailabilityCommand command);
}
