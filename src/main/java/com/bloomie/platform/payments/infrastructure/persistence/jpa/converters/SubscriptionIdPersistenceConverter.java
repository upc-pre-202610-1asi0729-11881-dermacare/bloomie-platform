package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts subscription ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class SubscriptionIdPersistenceConverter implements AttributeConverter<SubscriptionId, Long> {

    @Override
    public Long convertToDatabaseColumn(SubscriptionId attribute) {
        return attribute == null ? null : attribute.subscriptionId();
    }

    @Override
    public SubscriptionId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SubscriptionId(dbData);
    }
}
