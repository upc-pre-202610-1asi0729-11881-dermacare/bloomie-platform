package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.PaymentAmount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts payment amounts between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class PaymentAmountPersistenceConverter implements AttributeConverter<PaymentAmount, Double> {

    @Override
    public Double convertToDatabaseColumn(PaymentAmount attribute) {
        return attribute == null ? null : attribute.amount();
    }

    @Override
    public PaymentAmount convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new PaymentAmount(dbData);
    }
}
