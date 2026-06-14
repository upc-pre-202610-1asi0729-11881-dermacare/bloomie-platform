package com.bloomie.platform.payments.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;

/**
 * Static assembler between payment domain and persistence representations.
 */
public class PaymentPersistenceAssembler {

    PaymentPersistenceAssembler() {
    }

    public static Payment toDomainFromPersistence(PaymentPersistenceEntity entity) {
        if (entity == null) return null;
        return new Payment(
                entity.getId(),
                entity.getPatientId(),
                entity.getPlanId(),
                entity.getSubscriptionId(),
                entity.getType(),
                entity.getPaymentAmount(),
                entity.getStatus()
        );
    }

    public static PaymentPersistenceEntity toPersistenceFromDomain(Payment payment) {
        if (payment == null) return null;
        var entity = new PaymentPersistenceEntity();
        if (entity.getId() != null) {
            entity.setId(payment.getId());
        }
        entity.setPatientId(payment.getPatientIdValue());
        entity.setPlanId(payment.getPlanIdValue());
        entity.setSubscriptionId(payment.getSubscriptionIdValue());
        entity.setPaymentAmount(payment.getAmountValue());
        entity.setStatus(payment.getStatus());
        entity.setType(payment.getType());
        return entity;
    }
}
