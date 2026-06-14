package com.bloomie.platform.payments.domain.model.queries;

import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;

/**
 * Get Payments By Patient ID Query
 */
public record GetPaymentsByPatientIdQuery(PatientId patientId) {
}
