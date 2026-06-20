package com.bloomie.platform.dermatologicalappointment.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologicalappointment.domain.model.valueobjects.ClinicalNotes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ClinicalNotesPersistenceConverter implements AttributeConverter<ClinicalNotes, String> {

    @Override
    public String convertToDatabaseColumn(ClinicalNotes attribute) {
        return attribute == null ? "" : attribute.value();
    }

    @Override
    public ClinicalNotes convertToEntityAttribute(String dbData) {
        return new ClinicalNotes(dbData == null ? "" : dbData);
    }
}
