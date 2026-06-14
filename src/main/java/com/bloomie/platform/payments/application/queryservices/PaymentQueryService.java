package com.bloomie.platform.payments.application.queryservices;


import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;

import java.util.Optional;

public interface PaymentQueryService {
    Optional<Payment> handle(GetPaymentByIdQuery query);
    Optional<Payment> handle(GetPaymentsByPatientIdQuery query);
}
