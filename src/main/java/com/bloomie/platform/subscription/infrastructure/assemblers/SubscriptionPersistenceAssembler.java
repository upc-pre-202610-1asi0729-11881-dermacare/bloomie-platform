// SubscriptionPersistenceAssembler.java
package com.bloomie.platform.subscription.infrastructure.assemblers;

import com.bloomie.platform.subscription.domain.model.aggregates.Subscription;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.infrastructure.entities.SubscriptionPersistenceEntity;

public final class SubscriptionPersistenceAssembler {

    private SubscriptionPersistenceAssembler() {}

    public static Subscription toDomainFromPersistence(SubscriptionPersistenceEntity entity) {
        if (entity == null) return null;
        return new Subscription(
                entity.getId(),
                entity.getPatientId(),
                entity.getPlanId(),
                entity.getStatus(),
                entity.getStartDate(),
                entity.getEndDate());
    }

    public static SubscriptionPersistenceEntity toPersistenceFromDomain(Subscription subscription) {
        if (subscription == null) return null;
        var entity = new SubscriptionPersistenceEntity();
        if (subscription.getId() != null) {
            entity.setId(subscription.getId());
        }
        entity.setPatientId(subscription.getPatientIdValue());
        entity.setPlanId(subscription.getPlanIdValue());
        entity.setStatus(subscription.getStatus());
        entity.setStartDate(subscription.getStartDate());
        entity.setEndDate(subscription.getEndDate());
        return entity;
    }
}