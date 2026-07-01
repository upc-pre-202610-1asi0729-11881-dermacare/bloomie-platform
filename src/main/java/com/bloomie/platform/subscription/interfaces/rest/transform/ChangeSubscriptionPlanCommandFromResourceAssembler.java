package com.bloomie.platform.subscription.interfaces.rest.transform;

import com.bloomie.platform.subscription.domain.model.commands.ChangeSubscriptionPlanCommand;
import com.bloomie.platform.subscription.interfaces.rest.resources.ChangeSubscriptionPlanResource;

public class ChangeSubscriptionPlanCommandFromResourceAssembler {
    public static ChangeSubscriptionPlanCommand toCommandFromResource(Long subscriptionId, ChangeSubscriptionPlanResource resource) {
        return new ChangeSubscriptionPlanCommand(
                subscriptionId,
                resource.newPlanId()
        );
    }
}
