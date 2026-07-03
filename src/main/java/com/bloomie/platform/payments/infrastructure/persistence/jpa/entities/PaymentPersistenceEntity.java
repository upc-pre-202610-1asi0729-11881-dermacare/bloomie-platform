package com.bloomie.platform.payments.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.payments.domain.model.valueobjects.*;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.AppointmentIdPersistenceConverter;
import com.bloomie.platform.payments.infrastructure.persistence.jpa.converters.DermatologistIdPersistenceConverter;
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

    // Only set for SUBSCRIPTION and RENEWAL payments.
    @Convert(converter = PlanIdPersistenceConverter.class)
    private PlanId planId;

    // Only set for SUBSCRIPTION and RENEWAL payments.
    @Convert(converter = SubscriptionIdPersistenceConverter.class)
    private SubscriptionId subscriptionId;

    // Only set for CONSULTATION payments.
    @Convert(converter = AppointmentIdPersistenceConverter.class)
    private AppointmentId appointmentId;

    // Only set for CONSULTATION payments.
    @Convert(converter = DermatologistIdPersistenceConverter.class)
    private DermatologistId dermatologistId;

    @Convert(converter = PaymentAmountPersistenceConverter.class)
    @Column(nullable = false)
    private PaymentAmount paymentAmount;

    // The platform's monetization cut of paymentAmount. Only set for CONSULTATION payments.
    @Convert(converter = PaymentAmountPersistenceConverter.class)
    private PaymentAmount platformFeeAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;
}
