package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.PatientId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SkinProfileId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SkinProfileIdPersistenceConverter implements AttributeConverter<SkinProfileId, Long> {

    @Override
    public Long convertToDatabaseColumn(SkinProfileId attribute) {
        return attribute == null ? null : attribute.skinProfileId();
    }

    @Override
    public SkinProfileId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SkinProfileId(dbData);
    }
}
