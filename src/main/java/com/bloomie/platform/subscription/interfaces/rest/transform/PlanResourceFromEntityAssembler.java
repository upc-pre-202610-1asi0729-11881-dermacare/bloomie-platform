package com.bloomie.platform.subscription.interfaces.rest.transform;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.interfaces.rest.resources.PlanResource;

public class PlanResourceFromEntityAssembler {
    public static PlanResource toResourceFromEntity(Plan entity) {
        return new PlanResource(
                entity.getId(),
                entity.getType().name(),
                entity.getName(),
                entity.getPrice(),
                entity.getDurationDays(),
                entity.getModules());
    }
}
