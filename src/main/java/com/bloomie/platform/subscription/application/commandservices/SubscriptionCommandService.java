package com.bloomie.platform.subscription.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;

public interface SubscriptionCommandService {

    Result<Long, ApplicationError> handle(SelectSubscriptionPlanCommand command);
}
