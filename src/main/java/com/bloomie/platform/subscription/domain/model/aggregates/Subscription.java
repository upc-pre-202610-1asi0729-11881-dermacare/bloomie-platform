// Subscription.java
package com.bloomie.platform.subscription.domain.model.aggregates;

import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionPlanSelectedEvent;
import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import com.bloomie.platform.subscription.domain.model.valueobjects.SubscriptionStatus;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Getter @Setter
    private Long id;

    @Setter
    private PatientId patientId;

    @Setter
    private PlanId planId;

    @Setter
    private SubscriptionStatus status;

    @Setter
    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    public Subscription(Long id, PatientId patientId, PlanId planId,
                        SubscriptionStatus status, LocalDateTime startDate,
                        LocalDateTime endDate) {
        this.id = id;
        this.patientId = patientId;
        this.planId = planId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscription(SelectSubscriptionPlanCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.planId = new PlanId(command.planId());
        this.status = SubscriptionStatus.PENDING;
        this.startDate = null;
        this.endDate = null;
    }

    public void onPlanSelected() {
        registerDomainEvent(SubscriptionPlanSelectedEvent.from(this));
    }

    public Long getPatientId() { return patientId.patientId(); }
    public PatientId getPatientIdValue() { return patientId; }
    public Long getPlanId() { return planId.planId(); }
    public PlanId getPlanIdValue() { return planId; }
    public SubscriptionStatus getStatus() { return status; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }

    public boolean isActive() {
        return status == SubscriptionStatus.ACTIVE;
    }

    public boolean isExpired() {
        return endDate != null && LocalDateTime.now().isAfter(endDate);
    }
}