package com.bloomie.platform.payments.domain.model.commands;

/**
 * Command to process the payment for a dermatological consultation appointment.
 *
 * <p>Semantically distinct from {@link ProcessSubscriptionPaymentCommand} and
 * {@link ProcessRenewalPaymentCommand}: this one is billed against an appointment with a
 * dermatologist rather than a subscription plan, and it carries a platform fee cut on top
 * of the consultation fee.</p>
 *
 * @param patientId       the patient being billed
 * @param dermatologistId the dermatologist being paid out for the consultation
 * @param appointmentId   the appointment this payment is associated with
 * @param amount          the dermatologist's consultation fee to charge
 */
public record ProcessConsultationPaymentCommand(
        Long patientId,
        Long dermatologistId,
        Long appointmentId,
        Double amount) {}
