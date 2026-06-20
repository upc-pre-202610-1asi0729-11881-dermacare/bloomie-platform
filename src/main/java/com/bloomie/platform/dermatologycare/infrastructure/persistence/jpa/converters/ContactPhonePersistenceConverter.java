// ContactPhonePersistenceConverter.java
package com.bloomie.platform.dermatologycare.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.dermatologycare.domain.model.valueobjects.ContactPhone;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ContactPhonePersistenceConverter implements AttributeConverter<ContactPhone, String> {

    @Override
    public String convertToDatabaseColumn(ContactPhone attribute) {
        return attribute == null ? null : attribute.contactPhone();
    }

    @Override
    public ContactPhone convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new ContactPhone(dbData);
    }
}