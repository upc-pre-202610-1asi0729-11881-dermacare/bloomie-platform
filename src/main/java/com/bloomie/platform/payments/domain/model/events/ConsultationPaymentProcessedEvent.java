package com.bloomie.platform.payments.domain.model.events;

import com.bloomie.platform.payments.domain.model.aggregates.Payment;

/**
 * Domain event published when a dermatological consultation payment is successfully
 * processed and persisted.
 *
 * <p>Other bounded contexts can listen to this event to react to payment completion
 * without directly coupling to the {@code payments} application services.</p>
 *
 * @param paymentId         The identity assigned to the processed payment.
 * @param patientId         The patient who originated the payment.
 * @param dermatologistId   The dermatologist being paid out for the consultation.
 * @param appointmentId     The appointment for which the payment was made.
 * @param amount            The consultation fee charged to the patient.
 * @param platformFeeAmount The platform's monetization cut, already included in {@code amount}.
 */
public record ConsultationPaymentProcessedEvent(
        Long paymentId,
        Long patientId,
        Long dermatologistId,
        Long appointmentId,
        Double amount,
        Double platformFeeAmount) {

    /**
     * Convenience factory that extracts all needed fields from a saved {@link Payment}.
     *
     * @param payment the saved payment (must already carry a non-null id)
     * @return a fully populated {@link ConsultationPaymentProcessedEvent}
     */
    public static ConsultationPaymentProcessedEvent from(Payment payment) {
        return new ConsultationPaymentProcessedEvent(
                payment.getId(),
                payment.getPatientId(),
                payment.getDermatologistId(),
                payment.getAppointmentId(),
                payment.getAmount(),
                payment.getPlatformFeeAmount()
        );
    }
}
