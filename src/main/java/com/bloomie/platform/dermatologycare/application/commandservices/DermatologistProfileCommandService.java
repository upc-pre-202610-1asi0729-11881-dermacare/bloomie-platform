package com.bloomie.platform.dermatologycare.application.commandservices;

import com.bloomie.platform.dermatologycare.domain.model.aggregates.DermatologistProfile;
import com.bloomie.platform.dermatologycare.domain.model.commands.RegisterDermatologistProfileCommand;
import com.bloomie.platform.dermatologycare.domain.model.commands.UpdateDermatologistProfileCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service port for write operations on the {@link DermatologistProfile} aggregate.
 */
public interface DermatologistProfileCommandService {
    Result<Long, ApplicationError> handle(RegisterDermatologistProfileCommand command);
    Result<DermatologistProfile, ApplicationError> handle(UpdateDermatologistProfileCommand command);
}
