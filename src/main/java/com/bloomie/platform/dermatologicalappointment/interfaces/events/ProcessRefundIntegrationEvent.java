package com.bloomie.platform.dermatologicalAppointment.interfaces.events;

/**
 * Integration event published by the Dermatological Appointment bounded context when a
 * cancelled appointment qualifies for a refund.
 *
 * <p>This is the <em>published language</em> of the dermatological appointment context.
 * The Subscription bounded context should listen to this event to initiate the
 * refund processing flow.</p>
 *
 * @param appointmentId the id of the cancelled appointment eligible for refund
 * @param patientId     the IAM user id of the patient who should receive the refund
 */
public record ProcessRefundIntegrationEvent(Long appointmentId, Long patientId) {
}
