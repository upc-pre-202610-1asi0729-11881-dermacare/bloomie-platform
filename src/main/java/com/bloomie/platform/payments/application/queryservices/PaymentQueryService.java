package com.bloomie.platform.payments.application.queryservices;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByAppointmentIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentByIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentBySubscriptionIdQuery;
import com.bloomie.platform.payments.domain.model.queries.GetPaymentsByPatientIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Payments bounded-context read queries.
 */
public interface PaymentQueryService {
    /**
     * Handle Get Payment By ID Query
     *
     * @param query The {@link GetPaymentByIdQuery} query
     * @return A {@link Payment} instance if found, otherwise empty
     */
    Optional<Payment> handle(GetPaymentByIdQuery query);

    /**
     * Handle Get Payments By Patient ID Query
     *
     * @param query The {@link GetPaymentsByPatientIdQuery} query
     * @return A list of {@link Payment} instances for the given patient
     */
    List<Payment> handle(GetPaymentsByPatientIdQuery query);

    /**
     * Handle Get Payment By Subscription ID Query
     *
     * @param query The {@link GetPaymentBySubscriptionIdQuery} query
     * @return A {@link Payment} instance if found, otherwise empty
     */
    Optional<Payment> handle(GetPaymentBySubscriptionIdQuery query);

    /**
     * Handle Get Payment By Appointment ID Query
     *
     * @param query The {@link GetPaymentByAppointmentIdQuery} query
     * @return A {@link Payment} instance if found, otherwise empty
     */
    Optional<Payment> handle(GetPaymentByAppointmentIdQuery query);
}
