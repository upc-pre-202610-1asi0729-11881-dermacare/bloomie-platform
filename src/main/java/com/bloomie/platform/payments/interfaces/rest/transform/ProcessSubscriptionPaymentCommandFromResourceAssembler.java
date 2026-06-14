package com.bloomie.platform.payments.interfaces.rest.transform;

import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.interfaces.rest.resources.ProcessSubscriptionPaymentResource;

/**
 * Assembler to convert a ProcessSubscriptionPaymentResource to a ProcessSubscriptionPaymentCommand.
 */
public class ProcessSubscriptionPaymentCommandFromResourceAssembler {

    /**
     * Converts a ProcessSubscriptionPaymentResource to a ProcessSubscriptionPaymentCommand.
     *
     * @param resource The {@link ProcessSubscriptionPaymentResource} resource to convert.
     * @return The {@link ProcessSubscriptionPaymentCommand} command.
     */
    public static ProcessSubscriptionPaymentCommand toCommandFromResource(
            ProcessSubscriptionPaymentResource resource) {
        return new ProcessSubscriptionPaymentCommand(
                resource.patientId(),
                resource.planId(),
                resource.subscriptionId(),
                resource.paymentAmount());
    }
}
