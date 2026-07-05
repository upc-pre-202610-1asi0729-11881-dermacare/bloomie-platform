// Subscription.java
package com.bloomie.platform.subscription.domain.model.aggregates;

import com.bloomie.platform.subscription.domain.model.commands.SelectSubscriptionPlanCommand;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionActivatedEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionCancelledEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionExpiredEvent;
import com.bloomie.platform.subscription.domain.model.events.SubscriptionPlanChangedEvent;
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

    // Transient flags — not persisted. Let the repository detect state transitions
    // that cannot be inferred from status alone (e.g. PENDING→ACTIVE vs ACTIVE→ACTIVE).
    @Getter
    private boolean activating = false;
    @Getter
    private boolean renewing = false;
    @Getter
    private boolean changingPlan = false;
    @Getter
    private boolean resubscribing = false;

    // Not persisted: only holds the pre-mutation plan id for the duration of a plan change,
    // so it can be reported by SubscriptionPlanChangedEvent before being discarded.
    private PlanId previousPlanId;

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
     * Transitions this subscription from PENDING to {@link SubscriptionStatus#ACTIVE},
     * setting the billing period based on the plan's duration.
     *
     * @param durationDays number of days in the plan's billing cycle
     */
    public void activate(int durationDays) {
        this.status = SubscriptionStatus.ACTIVE;
        this.startDate = LocalDateTime.now();
        this.endDate = this.startDate.plusDays(durationDays);
        this.activating = true;
    }

    /**
     * Registers the {@link SubscriptionActivatedEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     */
    public void onActivated() {
        registerDomainEvent(SubscriptionActivatedEvent.from(this));
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
     * Switches this subscription to a different plan, capturing the previous plan id
     * so {@link #onPlanChanged(Long)} can report the transition.
     *
     * @param newPlanId the plan id to switch this subscription to
     */
    public void changePlan(PlanId newPlanId) {
        this.previousPlanId = this.planId;
        this.planId = newPlanId;
        // A plan change during the CANCELLED grace period means the patient wants to
        // keep going after all — un-cancel it.
        if (this.status == SubscriptionStatus.CANCELLED) {
            this.status = SubscriptionStatus.ACTIVE;
        }
        this.changingPlan = true;
    }

    /**
     * Registers the {@link SubscriptionPlanChangedEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     *
     * @param previousPlanId the plan id this subscription was previously on
     */
    public void onPlanChanged(Long previousPlanId) {
        registerDomainEvent(SubscriptionPlanChangedEvent.from(this, previousPlanId));
    }

    /**
     * Reactivates a lapsed (CANCELLED/EXPIRED) subscription onto a (possibly new) plan.
     *
     * <p>Reuses this same row instead of requiring a brand-new subscription, so a
     * returning patient isn't blocked by the one-subscription-per-patient constraint
     * when they resubscribe after cancelling or letting their plan expire.</p>
     *
     * @param newPlanId the plan id to resubscribe onto
     */
    public void resubscribe(PlanId newPlanId) {
        this.planId = newPlanId;
        this.status = SubscriptionStatus.PENDING;
        this.startDate = null;
        this.endDate = null;
        this.resubscribing = true;
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
    public Long getPreviousPlanId() { return previousPlanId == null ? null : previousPlanId.planId(); }
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