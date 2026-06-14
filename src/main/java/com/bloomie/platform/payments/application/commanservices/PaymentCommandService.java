package com.bloomie.platform.payments.application.commanservices;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Payment Command Service
 */
public interface PaymentCommandService {
    /**
     * Handle Process Subscription Payment Command
     *
     * @param command The {@link ProcessSubscriptionPaymentCommand} command
     * @return A {@link Result} containing the created {@link Payment} on success,
     *         or an {@link ApplicationError} on failure (validation or business rule violation)
     */
    Result<Payment, ApplicationError> handle(ProcessSubscriptionPaymentCommand command);
}
