package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.PatientId;
import com.bloomie.platform.payments.domain.model.valueobjects.PaymentAmount;
import com.bloomie.platform.payments.domain.model.valueobjects.PaymentType;
import com.bloomie.platform.payments.domain.model.valueobjects.SubscriptionId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class PaymentAmountPersistenceConverter implements AttributeConverter<PaymentAmount, Double>  {
    @Override
    public Double convertToDatabaseColumn(PaymentAmount attribute)  {
        return attribute == null ? null : attribute.amount();
    }

    @Override
    public PaymentAmount convertToEntityAttribute(Double dbData) {
        return dbData == null ? null : new PaymentAmount(dbData);
    }
}
