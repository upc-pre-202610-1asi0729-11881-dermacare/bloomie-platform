package com.bloomie.platform.subscription.infrastructure.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.bloomie.platform.subscription.infrastructure.converters.PatientIdPersistenceConverter;
import com.bloomie.platform.subscription.infrastructure.converters.PlanIdPersistenceConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Convert(converter = PatientIdPersistenceConverter.class)
    @Column(nullable = false)
    private PatientId patientId;

    @Convert(converter = PlanIdPersistenceConverter.class)
    @Column(nullable = false)
    private PlanId planId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
