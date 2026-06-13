package com.bloomie.platform.subscription.interfaces.rest.transform;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {
    public static SubscriptionResource toResourceFromEntity(Subscription entity) {
        return new SubscriptionResource(entity.getId(), entity.getPatientId(),
                entity.getPlanId(), entity.getStatus().name(), entity. getStartDate(), entity.getEndDate());
    }
}
