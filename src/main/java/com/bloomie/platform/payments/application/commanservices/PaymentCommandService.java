package com.bloomie.platform.payments.application.commanservices;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.commands.ProcessSubscriptionPaymentCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

public interface PaymentCommandService {
    Result<Payment, ApplicationError> handle(ProcessSubscriptionPaymentCommand command);
}
