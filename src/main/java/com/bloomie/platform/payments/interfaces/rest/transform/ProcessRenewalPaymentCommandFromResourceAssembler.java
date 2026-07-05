package com.bloomie.platform.payments.interfaces.rest.transform;

import com.bloomie.platform.payments.domain.model.commands.ProcessRenewalPaymentCommand;
import com.bloomie.platform.payments.interfaces.rest.resources.ProcessRenewalPaymentResource;

/**
 * Assembler that converts a {@link ProcessRenewalPaymentResource} REST resource
 * into a {@link ProcessRenewalPaymentCommand} domain command.
 */
public class ProcessRenewalPaymentCommandFromResourceAssembler {

    public static ProcessRenewalPaymentCommand toCommandFromResource(ProcessRenewalPaymentResource resource) {
        return new ProcessRenewalPaymentCommand(
                resource.patientId(),
                resource.planId(),
                resource.subscriptionId(),
                resource.amount());
    }
}
