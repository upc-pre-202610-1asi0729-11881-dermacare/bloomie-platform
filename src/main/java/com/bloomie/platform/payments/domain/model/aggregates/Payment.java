package com.bloomie.platform.payments.domain.model.aggregates;

import com.bloomie.platform.payments.domain.model.commands.ProcessRenewalPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.domain.model.events.PaymentRefundedEvent;
import com.bloomie.platform.payments.domain.model.events.SubscriptionPaymentProcessedEvent;
import com.bloomie.platform.payments.domain.model.events.SubscriptionRenewalPaymentProcessedEvent;
import com.bloomie.platform.payments.domain.model.valueobjects.*;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.payments.domain.model.valueobjects.PlanId;
import lombok.Getter;
import lombok.Setter;

/**
 * Payment aggregate root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration
 * support. No JPA or persistence annotation is present here — those concerns live
 * exclusively in {@code PaymentPersistenceEntity}.</p>
 */
@Getter
public class Payment extends AbstractDomainAggregateRoot<Payment> {
    @Setter
    Long id;

    @Setter
    PatientId patientId;

    @Setter
    PlanId planId;

    @Setter
    SubscriptionId subscriptionId;

    @Setter
    PaymentType type;

    @Setter
    PaymentAmount amount;

    @Setter
    PaymentStatus status;

    // Transient flag — not persisted. Lets the repository detect a refund transition
    // (status change to REFUNDED) without relying on status comparison alone.
    @Getter
    private boolean refunding = false;

    /**
     * Creates a payment from the provided domain values.
     */
    public Payment(Long id, PatientId patientId, PlanId planId, SubscriptionId subscriptionId, PaymentType type, PaymentAmount amount, PaymentStatus status) {
        this.id = id;
        this.patientId = patientId;
        this.planId = planId;
        this.subscriptionId = subscriptionId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    /**
     * Creates a payment from the provided {@link ProcessSubscriptionPaymentCommand}.
     *
     * @param command The {@link ProcessSubscriptionPaymentCommand} command
     */
    public Payment(ProcessSubscriptionPaymentCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.planId = new PlanId(command.planId());
        this.subscriptionId = new SubscriptionId(command.subscriptionId());
        this.amount = new PaymentAmount(command.amount());
        this.type = PaymentType.SUBSCRIPTION;
        this.status = PaymentStatus.PENDING;
    }

    /**
     * Creates a renewal payment from the provided {@link ProcessRenewalPaymentCommand}.
     *
     * @param command The {@link ProcessRenewalPaymentCommand} command
     */
    public Payment(ProcessRenewalPaymentCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.planId = new PlanId(command.planId());
        this.subscriptionId = new SubscriptionId(command.subscriptionId());
        this.amount = new PaymentAmount(command.amount());
        this.type = PaymentType.RENEWAL;
        this.status = PaymentStatus.PENDING;
    }

    /**
     * Signals that this initial subscription payment has been processed and persisted.
     * Registers a {@link SubscriptionPaymentProcessedEvent} for publication.
     */
    public void onProcessSubscriptionPayment() {
        this.status = PaymentStatus.PROCESSED;
        registerDomainEvent(SubscriptionPaymentProcessedEvent.from(this));
    }

    /**
     * Signals that this renewal payment has been processed and persisted.
     * Registers a {@link SubscriptionRenewalPaymentProcessedEvent} for publication.
     */
    public void onProcessRenewalPayment() {
        this.status = PaymentStatus.PROCESSED;
        registerDomainEvent(SubscriptionRenewalPaymentProcessedEvent.from(this));
    }

    /**
     * Transitions this payment to {@link PaymentStatus#REFUNDED}.
     * The caller is responsible for verifying the payment is in a refundable state.
     */
    public void refund() {
        this.status = PaymentStatus.REFUNDED;
        this.refunding = true;
    }

    /**
     * Registers the {@link PaymentRefundedEvent} domain event so the repository
     * can publish it after persisting the updated aggregate.
     */
    public void onRefunded() {
        registerDomainEvent(PaymentRefundedEvent.from(this));
    }

    /**
     * Patient id getter.
     *
     * @return Patient id
     */
    public Long getPatientId() {
        return patientId.patientId();
    }

    /**
     * Plan id getter.
     *
     * @return Plan id
     */
    public Long getPlanId() {
        return planId.planId();
    }

    /**
     * Payment amount getter.
     *
     * @return Payment amount
     */
    public Double getAmount() {
        return amount.amount();
    }

    /**
     * Subscription id getter.
     *
     * @return Subscription id
     */
    public Long getSubscriptionId() { return subscriptionId.subscriptionId(); }

    public PatientId getPatientIdValue() { return patientId; }
    public PlanId getPlanIdValue() { return planId; }
    public SubscriptionId getSubscriptionIdValue() { return subscriptionId; }
    public PaymentAmount getAmountValue() { return amount; }
}
