package com.bloomie.platform.payments.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.payments.domain.model.valueobjects.*;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.PaymentAmountPersistenceConverter;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.PlanIdPersistenceConverter;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.SubscriptionIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for payments.
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class PaymentPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(nullable = false)
    private PatientId patientId;

    @Convert(converter = PlanIdPersistenceConverter.class)
    @Column(nullable = false)
    private PlanId planId;

    @Convert(converter = SubscriptionIdPersistenceConverter.class)
    @Column(nullable = false)
    private SubscriptionId subscriptionId;

    @Convert(converter = PaymentAmountPersistenceConverter.class)
    @Column(nullable = false)
    private PaymentAmount paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
}
