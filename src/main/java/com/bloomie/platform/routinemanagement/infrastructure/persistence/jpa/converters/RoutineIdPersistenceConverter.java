package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.RoutineId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link RoutineId} to/from a {@code Long} column for JPA persistence.
 */
@Converter(autoApply = false)
public class RoutineIdPersistenceConverter implements AttributeConverter<RoutineId, Long> {

    @Override
    public Long convertToDatabaseColumn(RoutineId attribute) {
        return attribute == null ? null : attribute.routineId();
    }

    @Override
    public RoutineId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new RoutineId(dbData);
    }
}
