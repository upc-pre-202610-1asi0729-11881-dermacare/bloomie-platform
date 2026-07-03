package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.DermatologistId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts dermatologist ids between the domain model and persistence column values.
 */
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
