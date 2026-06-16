package com.bloomie.platform.dermatologycare.domain.model.valueobjects;

public record LicenseNumber(String licenseNumber) {
    private static final String LICENSE_NUMBER_BLANK = "dermatology.license.number.blank";
    private static final String LICENSE_NUMBER_LONG = "dermatology.license.number.long";

    public LicenseNumber {
        if (licenseNumber == null || licenseNumber.isBlank()){
            throw new IllegalArgumentException(LICENSE_NUMBER_BLANK);
        }
        if (licenseNumber.length() > 50) {
            throw new IllegalArgumentException(LICENSE_NUMBER_LONG);
        }
    }
}
