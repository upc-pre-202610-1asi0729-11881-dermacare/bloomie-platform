package com.bloomie.platform.payments.domain.model.queries;

import com.bloomie.platform.payments.domain.model.valueobjects.AppointmentId;

/**
 * Get Payment By Appointment ID Query
 */
public record GetPaymentByAppointmentIdQuery(AppointmentId appointmentId) {
}
