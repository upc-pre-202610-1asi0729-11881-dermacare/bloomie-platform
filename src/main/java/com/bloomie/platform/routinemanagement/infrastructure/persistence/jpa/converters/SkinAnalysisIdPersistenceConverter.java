package com.bloomie.platform.routinemanagement.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.routinemanagement.domain.model.valueobjects.SkinAnalysisId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link SkinAnalysisId} to/from a {@code Long} column for JPA persistence.
 */
@Converter(autoApply = false)
public class SkinAnalysisIdPersistenceConverter implements AttributeConverter<SkinAnalysisId, Long> {

    @Override
    public Long convertToDatabaseColumn(SkinAnalysisId attribute) {
        return attribute == null ? null : attribute.skinAnalysisId();
    }

    @Override
    public SkinAnalysisId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SkinAnalysisId(dbData);
    }
}