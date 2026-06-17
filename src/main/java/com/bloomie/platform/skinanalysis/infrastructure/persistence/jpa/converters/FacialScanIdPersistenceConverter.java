package com.bloomie.platform.skinanalysis.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.skinanalysis.domain.model.valueobjects.FacialScanId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link FacialScanId} to/from a {@code Long} column for JPA persistence.
 */
@Converter(autoApply = false)
public class FacialScanIdPersistenceConverter implements AttributeConverter<FacialScanId, Long> {

    @Override
    public Long convertToDatabaseColumn(FacialScanId attribute) {
        return attribute == null ? null : attribute.facialScanId();
    }

    @Override
    public FacialScanId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new FacialScanId(dbData);
    }
}
