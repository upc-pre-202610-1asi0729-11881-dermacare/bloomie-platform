package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.PatientId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link PatientId} to/from a {@code Long} column for JPA persistence.
 */
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