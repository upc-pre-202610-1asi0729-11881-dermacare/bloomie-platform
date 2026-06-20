package com.bloomie.platform.iam.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.iam.domain.model.valueobjects.HashedPassword;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA attribute converter that maps between the {@link HashedPassword} value object
 * and its plain {@link String} representation in the database.
 *
 * <p>{@code autoApply = false} is intentional: the converter is applied explicitly
 * with {@code @Convert} on the entity field so the mapping is visible at the declaration site.</p>
 */
@Converter(autoApply = false)
public class HashedPasswordPersistenceConverter implements AttributeConverter<HashedPassword, String> {
    @Override
    public String convertToDatabaseColumn(HashedPassword attribute) {
        return attribute == null? null: attribute.value();
    }

    @Override
    public HashedPassword convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new HashedPassword(dbData);
    }
}
