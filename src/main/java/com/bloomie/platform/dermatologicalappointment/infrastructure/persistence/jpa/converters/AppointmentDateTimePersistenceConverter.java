package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.AppointmentDateTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class AppointmentDateTimePersistenceConverter implements AttributeConverter<AppointmentDateTime, String> {

    @Override
    public String convertToDatabaseColumn(AppointmentDateTime attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public AppointmentDateTime convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new AppointmentDateTime(dbData);
    }
}
