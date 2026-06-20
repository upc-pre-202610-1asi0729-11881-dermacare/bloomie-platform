package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.DermatologistId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class DermatologistIdPersistenceConverter implements AttributeConverter<DermatologistId, Long> {

    @Override
    public Long convertToDatabaseColumn(DermatologistId attribute) {
        return attribute == null ? null : attribute.dermatologistId();
    }

    @Override
    public DermatologistId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new DermatologistId(dbData);
    }
}
