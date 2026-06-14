package com.bloomie.platform.payments.domain.model.aggregates;

import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.domain.model.events.SubscriptionPaymentProcessedEvent;
import com.bloomie.platform.payments.domain.model.valueobjects.*;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bloomie.platform.payments.domain.model.valueobjects.PlanId;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Payment extends AbstractDomainAggregateRoot<Payment> {
    @Setter
    Long id;

    @Setter
    PatientId patientId;

    @Setter
    PlanId planId;

    @Setter
    PaymentType type;

    @Setter
    PaymentAmount amount;

    @Setter
    PaymentStatus status;

    public Payment(Long id, PatientId patientId, PlanId planId, PaymentType type, PaymentAmount amount, PaymentStatus status) {
        this.id = id;
        this.patientId = patientId;
        this.planId = planId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public Payment(ProcessSubscriptionPaymentCommand command) {
        this.patientId = new PatientId(command.patientId());
        this.planId = new PlanId(command.planId());
        this.amount = new PaymentAmount(command.amount());
    }

    public void onProcessSubscriptionPayment() {
        registerDomainEvent(SubscriptionPaymentProcessedEvent.from(this));
    }

    public Long getPatientId() {
        return patientId.patientId();
    }

    public Long getPlanId() {
        return planId.planId();
    }

    public Double getAmount() {
        return amount.amount();
    }
}
