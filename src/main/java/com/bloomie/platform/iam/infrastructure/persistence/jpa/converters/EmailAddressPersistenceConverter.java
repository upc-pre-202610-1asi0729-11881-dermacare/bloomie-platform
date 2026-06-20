package com.bloomie.platform.iam.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.iam.domain.model.valueobjects.EmailAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.Email;

/**
 * JPA attribute converter that maps between the {@link EmailAddress} value object
 * and its plain {@link String} representation in the database.
 *
 * <p>{@code autoApply = false} is intentional: the converter is applied explicitly
 * with {@code @Convert} on the entity field so the mapping is visible at the declaration site.</p>
 */
@Converter(autoApply = false)
public class EmailAddressPersistenceConverter implements AttributeConverter<EmailAddress, String> {
    @Override
    public String convertToDatabaseColumn(EmailAddress attribute) {
        return attribute == null? null : attribute.address();
    }

    @Override
    public EmailAddress convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EmailAddress(dbData);
    }
}
