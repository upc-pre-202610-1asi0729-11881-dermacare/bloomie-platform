package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PatientIdPersistenceConverter implements AttributeConverter<PatientId, Long> {

    @Override
    public Long convertToDatabaseColumn(PatientId attribute) {
        return attribute == null ? null : attribute.patientId();
    }

    @Override
    public PatientId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PatientId(dbData);
    }
}
