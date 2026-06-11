package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.LicenseNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LicenseNumberPersistenceConverter implements AttributeConverter<LicenseNumber, String> {
    @Override
    public String convertToDatabaseColumn(LicenseNumber attribute) {
        return attribute == null ? null : attribute.licenseNumber();
    }

    @Override
    public LicenseNumber convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new LicenseNumber(dbData);
    }
}
