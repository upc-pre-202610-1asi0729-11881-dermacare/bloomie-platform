// GetSubscriptionByPatientIdQuery.java
package com.bloomie.platform.subscription.domain.model.queries;

import com.bloomie.platform.subscription.domain.model.valueobjects.PatientId;

public record GetSubscriptionByPatientIdQuery(PatientId patientId) {
    public GetSubscriptionByPatientIdQuery {
        if (patientId == null || patientId.patientId() == null || patientId.patientId() < 1)
            throw new IllegalArgumentException("PatientId cannot be null or less than 1");
    }
}