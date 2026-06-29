package com.bloomie.platform.subscription.application.commandservices;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.commands.ActivateSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.CancelSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.ExpireSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.RenewSubscriptionCommand;
import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;

public interface SubscriptionCommandService {

    Result<Long, ApplicationError> handle(SelectSubscriptionPlanCommand command);

    Result<Subscription, ApplicationError> handle(ActivateSubscriptionCommand command);

    Result<Subscription, ApplicationError> handle(CancelSubscriptionCommand command);

    Result<Subscription, ApplicationError> handle(RenewSubscriptionCommand command);

    Result<Subscription, ApplicationError> handle(ExpireSubscriptionCommand command);
}
