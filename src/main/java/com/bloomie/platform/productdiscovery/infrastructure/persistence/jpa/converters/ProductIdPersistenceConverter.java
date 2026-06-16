package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.converters;

import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts product IDs between the domain model and persistence column values.
 */
@Converter(autoApply = false)
public class ProductIdPersistenceConverter implements AttributeConverter<ProductId, Long> {

    @Override
    public Long convertToDatabaseColumn(ProductId attribute) {
        return attribute == null ? null : attribute.productId();
    }

    @Override
    public ProductId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : new ProductId(dbData);
    }
}