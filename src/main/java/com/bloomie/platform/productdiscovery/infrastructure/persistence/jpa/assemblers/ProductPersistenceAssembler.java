package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;

/**
 * Static assembler between product domain and persistence representations.
 */
public final class ProductPersistenceAssembler {

    public static Product toDomainFromPersistence(ProductPersistenceEntity entity) {
        if (entity == null) return null;
        var product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setBrand(entity.getBrand());
        product.setCategory(entity.getCategory());
        product.setDescription(entity.getDescription());
        product.setBenefits(entity.getBenefits());
        product.setAiRecommended(entity.isAiRecommended());
        product.setImageUrl(entity.getImageUrl() != null ? entity.getImageUrl() : "");
        return product;
    }

    public static ProductPersistenceEntity toPersistenceFromDomain(Product product) {
        if (product == null) return null;
        var entity = new ProductPersistenceEntity();
        if (product.getId() != null) entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setBrand(product.getBrand());
        entity.setCategory(product.getCategory());
        entity.setDescription(product.getDescription());
        entity.setBenefits(product.getBenefits());
        entity.setAiRecommended(product.isAiRecommended());
        entity.setImageUrl(product.getImageUrl());
        return entity;
    }
}