package com.bloomie.platform.payments.domain.model.aggregates;

import com.bloomie.platform.payments.domain.model.commands.ProcessConsultationPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.ProcessRenewalPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.domain.model.events.ConsultationPaymentProcessedEvent;
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

    /** Percentage of a consultation fee retained by the platform as its monetization cut. */
    private static final double PLATFORM_FEE_PERCENTAGE = 0.15;

    @Setter
    Long id;

    @Setter
    PatientId patientId;

    /** Only set for {@link PaymentType#SUBSCRIPTION} and {@link PaymentType#RENEWAL} payments. */
    @Setter
    PlanId planId;

    /** Only set for {@link PaymentType#SUBSCRIPTION} and {@link PaymentType#RENEWAL} payments. */
    @Setter
    SubscriptionId subscriptionId;

    /** Only set for {@link PaymentType#CONSULTATION} payments. */
    @Setter
    AppointmentId appointmentId;

    /** Only set for {@link PaymentType#CONSULTATION} payments. */
    @Setter
    DermatologistId dermatologistId;

    @Setter
    PaymentType type;

    @Setter
    PaymentAmount amount;

    /** The platform's monetization cut of {@link #amount}. Only set for {@link PaymentType#CONSULTATION} payments. */
    @Setter
    PaymentAmount platformFeeAmount;

    @Setter
    PaymentStatus status;

    // Transient flag — not persisted. Lets the repository detect a refund transition
    // (status change to REFUNDED) without relying on status comparison alone.
    @Getter
    private boolean refunding = false;

    /**
     * Creates a payment from the provided domain values.
     */
    public Payment(Long id, PatientId patientId, PlanId planId, SubscriptionId subscriptionId,
                   AppointmentId appointmentId, DermatologistId dermatologistId,
                   PaymentType type, PaymentAmount amount, PaymentAmount platformFeeAmount, PaymentStatus status) {
        this.id = id;
        this.patientId = patientId;
        this.planId = planId;
        this.subscriptionId = subscriptionId;
        this.appointmentId = appointmentId;
        this.dermatologistId = dermatologistId;
        this.type = type;
        this.amount = amount;
        this.platformFeeAmount = platformFeeAmount;
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
     * Creates a consultation payment from the provided {@link ProcessConsultationPaymentCommand}.
     *
     * <p>The platform fee is computed up front as {@link #PLATFORM_FEE_PERCENTAGE} of the
     * consultation fee — it is the platform's monetization cut, already included in {@code amount}
     * rather than charged on top of it.</p>
     *
     * @param command The {@link ProcessConsultationPaymentCommand} command
     */
    public Payment(ProcessConsultationPaymentCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.dermatologistId = new DermatologistId(command.dermatologistId());
        this.appointmentId = new AppointmentId(command.appointmentId());
        this.amount = new PaymentAmount(command.amount());
        this.platformFeeAmount = new PaymentAmount(command.amount() * PLATFORM_FEE_PERCENTAGE);
        this.type = PaymentType.CONSULTATION;
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
     * Signals that this consultation payment has been processed and persisted.
     * Registers a {@link ConsultationPaymentProcessedEvent} for publication.
     */
    public void onProcessConsultationPayment() {
        this.status = PaymentStatus.PROCESSED;
        registerDomainEvent(ConsultationPaymentProcessedEvent.from(this));
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
     * Plan id getter. Only present for SUBSCRIPTION and RENEWAL payments.
     *
     * @return Plan id, or {@code null} for CONSULTATION payments
     */
    public Long getPlanId() {
        return planId == null ? null : planId.planId();
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
     * Subscription id getter. Only present for SUBSCRIPTION and RENEWAL payments.
     *
     * @return Subscription id, or {@code null} for CONSULTATION payments
     */
    public Long getSubscriptionId() { return subscriptionId == null ? null : subscriptionId.subscriptionId(); }

    /**
     * Appointment id getter. Only present for CONSULTATION payments.
     *
     * @return Appointment id, or {@code null} for SUBSCRIPTION and RENEWAL payments
     */
    public Long getAppointmentId() { return appointmentId == null ? null : appointmentId.appointmentId(); }

    /**
     * Dermatologist id getter. Only present for CONSULTATION payments.
     *
     * @return Dermatologist id, or {@code null} for SUBSCRIPTION and RENEWAL payments
     */
    public Long getDermatologistId() { return dermatologistId == null ? null : dermatologistId.dermatologistId(); }

    /**
     * Platform fee amount getter. Only present for CONSULTATION payments.
     *
     * @return the platform's monetization cut, or {@code null} for SUBSCRIPTION and RENEWAL payments
     */
    public Double getPlatformFeeAmount() { return platformFeeAmount == null ? null : platformFeeAmount.amount(); }

    public PatientId getPatientIdValue() { return patientId; }
    public PlanId getPlanIdValue() { return planId; }
    public SubscriptionId getSubscriptionIdValue() { return subscriptionId; }
    public AppointmentId getAppointmentIdValue() { return appointmentId; }
    public DermatologistId getDermatologistIdValue() { return dermatologistId; }
    public PaymentAmount getAmountValue() { return amount; }
    public PaymentAmount getPlatformFeeAmountValue() { return platformFeeAmount; }
}
