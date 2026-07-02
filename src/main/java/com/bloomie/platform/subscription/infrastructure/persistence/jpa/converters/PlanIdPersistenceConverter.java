package com.bloomie.platform.subscription.infrastructure.converters;

import com.bloomie.platform.subscription.domain.model.entities.Plan;
import com.bloomie.platform.subscription.domain.model.valueobjects.PlanId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PlanIdPersistenceConverter  implements AttributeConverter<PlanId, Long> {
    @Override
    public Long convertToDatabaseColumn(PlanId attribute) {
        return attribute == null ? null : attribute.planId();
    }

    @Override
    public PlanId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new PlanId(dbData);
    }
}
