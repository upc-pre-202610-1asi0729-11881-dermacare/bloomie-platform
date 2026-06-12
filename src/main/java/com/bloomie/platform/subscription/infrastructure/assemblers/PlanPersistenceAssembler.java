package com.bloomie.platform.subscription.infrastructure.assemblers;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.infrastructure.entities.PlanPersistenceEntity;
import com.bloomie.platform.subscription.infrastructure.entities.SubscriptionPersistenceEntity;

import java.util.ArrayList;

public class PlanPersistenceAssembler {
    private PlanPersistenceAssembler() {
    }

    public static Plan toDomainFromPersistence(PlanPersistenceEntity entity) {
        if (entity == null) return null;
        return new Plan(
                entity.getId(),
                entity.getType(),
                entity.getName(),
                entity.getPrice(),
                entity.getDurationDays(),
                new ArrayList<>(entity.getModules()));
    }

    public static PlanPersistenceEntity toPersistenceFromDomain(Plan plan) {
        if (plan == null) return null;
        var entity = new PlanPersistenceEntity();
        if (plan.getId() != null) {
            entity.setId(plan.getId());
        }
        entity.setType(plan.getType());
        entity.setName(plan.getName());
        entity.setPrice(plan.getPrice());
        entity.setDurationDays(plan.getDurationDays());
        entity.setModules(new ArrayList<>(plan.getModules()));
        return entity;
    }
}
