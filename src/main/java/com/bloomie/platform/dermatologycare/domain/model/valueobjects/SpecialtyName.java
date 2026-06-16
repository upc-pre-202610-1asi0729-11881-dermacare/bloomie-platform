package com.bloomie.platform.dermatologycare.domain.model.valueobjects;

public record SpecialtyName(String specialtyName) {
    private static final String SPECIALTY_OVER_100_KEY = "dermatology.specialityName.long";
    private static final String SPECIALTY_BLANK_KEY = "dermatology.specialityName.blank";
    public SpecialtyName {
        if (specialtyName == null || specialtyName.isBlank()) {
            throw new IllegalArgumentException(SPECIALTY_BLANK_KEY);
        }
        if (specialtyName.length() > 100) {
            throw new IllegalArgumentException(SPECIALTY_OVER_100_KEY);
        }
    }
}
