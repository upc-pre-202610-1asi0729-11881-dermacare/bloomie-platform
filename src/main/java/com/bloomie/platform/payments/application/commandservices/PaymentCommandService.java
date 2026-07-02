package com.bloomie.platform.payments.application.commandservices;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessRenewalPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.payments.domain.model.commands.RefundPaymentCommand;
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
     *         or an {@link ApplicationError} on failure
     */
    Result<Payment, ApplicationError> handle(ProcessSubscriptionPaymentCommand command);

    /**
     * Handle Process Renewal Payment Command
     *
     * @param command The {@link ProcessRenewalPaymentCommand} command
     * @return A {@link Result} containing the created renewal {@link Payment} on success,
     *         or an {@link ApplicationError} on failure
     */
    Result<Payment, ApplicationError> handle(ProcessRenewalPaymentCommand command);

    /**
     * Handle Refund Payment Command
     *
     * @param command The {@link RefundPaymentCommand} command
     * @return A {@link Result} containing the refunded {@link Payment} on success,
     *         or an {@link ApplicationError} on failure
     */
    Result<Payment, ApplicationError> handle(RefundPaymentCommand command);
}
