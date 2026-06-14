package com.bloomie.platform.payments.interfaces.rest.transform;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.interfaces.rest.resources.PaymentResource;

/**
 * Assembler to convert a Payment entity to a PaymentResource.
 */
public class PaymentResourceFromEntityAssembler {

    /**
     * Converts a Payment entity to a PaymentResource.
     *
     * @param entity The {@link Payment} entity to convert.
     * @return The {@link PaymentResource} resource.
     */
    public static PaymentResource toResourceFromEntity(Payment entity) {
        return new PaymentResource(
                entity.getId(),
                entity.getPatientId(),
                entity.getPlanId(),
                entity.getSubscriptionId(),
                entity.getType().name(),
                entity.getAmount(),
                entity.getStatus().name());
    }
}
