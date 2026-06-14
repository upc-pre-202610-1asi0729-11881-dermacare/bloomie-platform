package com.bloomie.platform.payments.interfaces.rest.transform;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(entity.getId(), entity.getPatientId(), entity.getPlanId(), entity.getSubscriptionId(), entity.getType().name(), entity.getAmount(), entity.getStatus().name());
    }
}
