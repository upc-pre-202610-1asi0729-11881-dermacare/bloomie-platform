package com.bloomie.platform.dermatologyCare.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologyCare.domain.model.valueobjects.SpecialtyName;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SpecialtyNamePersistenceConverter implements AttributeConverter<SpecialtyName, String> {
    @Override
    public String convertToDatabaseColumn(SpecialtyName attribute) {
        return attribute == null ? null : attribute.specialtyName();
    }

    @Override
    public SpecialtyName convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new SpecialtyName(dbData);
    }
}
