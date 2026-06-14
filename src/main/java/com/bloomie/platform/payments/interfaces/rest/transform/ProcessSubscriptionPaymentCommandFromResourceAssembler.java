package com.bloomie.platform.payments.interfaces.rest.transform;

import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.interfaces.rest.resources.ProcessSubscriptionPaymentResource;

public class ProcessSubscriptionPaymentCommandFromResourceAssembler {
    public static ProcessSubscriptionPaymentCommand toCommandFromResource(
            ProcessSubscriptionPaymentResource resource) {
        return new ProcessSubscriptionPaymentCommand(
                resource.patientId(),
                resource.planId(),
                resource.subscriptionId(),
                resource.paymentAmount());
    }
}
