package com.bloomie.platform.intelligentsupport.application.commandservices;

import com.bloomie.platform.intelligentsupport.domain.model.aggregates.SupportQuery;
import com.bloomie.platform.intelligentsupport.domain.model.commands.CreateSupportQueryCommand;
import com.bloomie.platform.intelligentsupport.domain.model.commands.UpdateSupportQueryStatusCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

public interface SupportQueryCommandService {
    Result<SupportQuery, ApplicationError> handle(CreateSupportQueryCommand command);
    Result<SupportQuery, ApplicationError> handle(UpdateSupportQueryStatusCommand command);
}