package com.bloomie.platform.dermatologyCare.domain.model.valueobjects;

public record DermatologistId(Long dermatologistId) {
    private static final String DERMATOLOGIST_ID_BLANK_KEY = "dermatology.id.blank";
    public DermatologistId {
        if (dermatologistId == null || dermatologistId < 1) {
            throw new IllegalArgumentException(DERMATOLOGIST_ID_BLANK_KEY);
        }
    }
}
