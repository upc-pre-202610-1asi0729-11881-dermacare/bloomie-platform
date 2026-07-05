package com.bloomie.platform.payments.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.payments.domain.model.valueobjects.AppointmentId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts appointment ids between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class AppointmentIdPersistenceConverter implements AttributeConverter<AppointmentId, Long> {

    @Override
    public Long convertToDatabaseColumn(AppointmentId attribute) {
        return attribute == null ? null : attribute.appointmentId();
    }

    @Override
    public AppointmentId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new AppointmentId(dbData);
    }
}
