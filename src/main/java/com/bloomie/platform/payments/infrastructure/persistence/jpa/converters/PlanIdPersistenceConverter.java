package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.PlanId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts plan ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class PlanIdPersistenceConverter implements AttributeConverter<PlanId, Long> {

    @Override
    public Long convertToDatabaseColumn(PlanId attribute) {
        return attribute == null ? null : attribute.planId();
    }

    @Override
    public PlanId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PlanId(dbData);
    }
}
