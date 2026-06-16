package com.bloomie.platform.skinAnalysis.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.skinAnalysis.domain.model.valueobjects.PatientId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PatientIdPersistenceConverter implements AttributeConverter<PatientId, Long> {

    @Override
    public Long convertToDatabaseColumn(PatientId attribute) {
        return attribute == null ? null : attribute.patient_id();
    }

    @Override
    public PatientId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PatientId(dbData);
    }
}
