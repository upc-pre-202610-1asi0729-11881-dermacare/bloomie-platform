package com.bloomie.platform.subscription.interfaces.rest.transform;

import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.interfaces.rest.resources.SelectSubscriptionPlanResource;

public class SelectSubscriptionPlanCommandFromResourceAssembler {
    public static SelectSubscriptionPlanCommand toCommandFromResource(SelectSubscriptionPlanResource resource) {
        return new SelectSubscriptionPlanCommand(
                resource.patientId(),
                resource.plandId()
        );
    }
}
