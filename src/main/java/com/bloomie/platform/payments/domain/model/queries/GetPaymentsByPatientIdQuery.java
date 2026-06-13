package com.bloomie.platform.payments.domain.model.queries;

import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;

public record GetPaymentsByPatientIdQuery(PatientId patientId) {
}
