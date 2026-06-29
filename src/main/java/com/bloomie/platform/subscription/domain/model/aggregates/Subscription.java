// Subscription.java
package com.bloomie.platform.subscription.domain.model.aggregates;

import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionCancelledEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionExpiredEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionPlanSelectedEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionRenewedEvent;
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

    // Transient flag — not persisted. Lets the repository detect a renewal transition
    // (ACTIVE → ACTIVE) which cannot be inferred from status alone.
    @Getter
    private boolean renewing = false;

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

    /**
     * Transitions this subscription to {@link SubscriptionStatus#CANCELLED}.
     * The caller is responsible for verifying the subscription is in a cancellable state
     * before invoking this method.
     */
    public void cancel() {
        this.status = SubscriptionStatus.CANCELLED;
    }

    /**
     * Registers the {@link SubscriptionCancelledEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     */
    public void onCancelled() {
        registerDomainEvent(SubscriptionCancelledEvent.from(this));
    }

    /**
     * Extends this subscription's end date by {@code durationDays}.
     *
     * <p>If the current {@code endDate} is in the past (or null), the new period starts
     * from now. Otherwise it extends from the current {@code endDate}, so consecutive
     * renewals stack correctly.</p>
     *
     * @param durationDays number of days to add, obtained from the associated {@link com.bloomie.platform.subscription.domain.model.entities.Plan}
     */
    public void renew(int durationDays) {
        LocalDateTime base = (this.endDate == null || LocalDateTime.now().isAfter(this.endDate))
                ? LocalDateTime.now()
                : this.endDate;
        this.endDate = base.plusDays(durationDays);
        this.renewing = true;
    }

    /**
     * Registers the {@link SubscriptionRenewedEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     */
    public void onRenewed() {
        registerDomainEvent(SubscriptionRenewedEvent.from(this));
    }

    /**
     * Transitions this subscription to {@link SubscriptionStatus#EXPIRED}.
     * The caller is responsible for verifying the subscription is in an expirable state
     * before invoking this method.
     */
    public void expire() {
        this.status = SubscriptionStatus.EXPIRED;
    }

    /**
     * Registers the {@link SubscriptionExpiredEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     */
    public void onExpired() {
        registerDomainEvent(SubscriptionExpiredEvent.from(this));
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