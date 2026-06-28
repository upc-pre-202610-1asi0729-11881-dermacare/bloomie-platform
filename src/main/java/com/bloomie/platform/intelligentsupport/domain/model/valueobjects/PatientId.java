package com.bloomie.platform.intelligentsupport.domain.model.valueobjects;

public record PatientId(Long patientId) {
    private static final String INVALID_MESSAGE_KEY = "intelligent.support.patient.id.invalid";
    public PatientId {
        if (patientId == null || patientId < 1)
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
    }
}
