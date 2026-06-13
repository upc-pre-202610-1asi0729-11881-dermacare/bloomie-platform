// PatientId.java
package com.bloomie.platform.subscription.domain.model.valueobjects;

public record PatientId(Long patientId) {

    public PatientId {
        if (patientId == null || patientId < 1)
            throw new IllegalArgumentException("PatientId cannot be null or less than 1");
    }
}