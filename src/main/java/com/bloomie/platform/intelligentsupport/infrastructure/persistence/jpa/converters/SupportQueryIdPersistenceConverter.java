package com.bloomie.platform.intelligentsupport.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SkinProfileId;
import com.bloomie.platform.intelligentsupport.domain.model.valueobjects.SupportQueryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class SupportQueryIdPersistenceConverter implements AttributeConverter<SupportQueryId, Long> {

    @Override
    public Long convertToDatabaseColumn(SupportQueryId attribute) {
        return attribute == null ? null : attribute.supportQueryId();
    }

    @Override
    public SupportQueryId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new SupportQueryId(dbData);
    }
}
