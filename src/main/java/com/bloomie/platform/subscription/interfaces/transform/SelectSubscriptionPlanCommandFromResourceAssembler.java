package com.bloomie.platform.subscription.interfaces.transform;

import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.interfaces.resources.SelectSubscriptionPlanResource;

public class SelectSubscriptionPlanCommandFromResourceAssembler {
    public static SelectSubscriptionPlanCommand toCommandFromResource(SelectSubscriptionPlanResource resource) {
        return new SelectSubscriptionPlanCommand(
                resource.patientId(),
                resource.plandId()
        );
    }
}
