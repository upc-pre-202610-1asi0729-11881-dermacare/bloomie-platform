package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities.ProductCompatibilityPersistenceEntity;

/**
 * Static assembler that converts between the {@link ProductCompatibility} domain aggregate
 * and its {@link ProductCompatibilityPersistenceEntity} JPA counterpart.
 */
public final class ProductCompatibilityPersistenceAssembler {

    private ProductCompatibilityPersistenceAssembler() {}

    /**
     * Converts a JPA persistence entity into a domain aggregate.
     *
     * @param entity the JPA entity to convert; if {@code null} returns {@code null}
     * @return the reconstructed domain aggregate
     */
    public static ProductCompatibility toDomainFromPersistence(ProductCompatibilityPersistenceEntity entity) {
        if (entity == null) return null;
        var compatibility = new ProductCompatibility();
        compatibility.setId(entity.getId());
        compatibility.setProductId(entity.getProductId());
        compatibility.setSkinType(entity.getSkinType());
        compatibility.setCompatibilityScore(entity.getCompatibilityScore());
        compatibility.setReason(entity.getReason());
        return compatibility;
    }

    /**
     * Converts a domain aggregate into a JPA persistence entity.
     *
     * @param compatibility the domain aggregate to convert; if {@code null} returns {@code null}
     * @return the JPA entity ready for persistence
     */
    public static ProductCompatibilityPersistenceEntity toPersistenceFromDomain(ProductCompatibility compatibility) {
        if (compatibility == null) return null;
        var entity = new ProductCompatibilityPersistenceEntity();
        if (compatibility.getId() != null) entity.setId(compatibility.getId());
        entity.setProductId(compatibility.getProductId());
        entity.setSkinType(compatibility.getSkinType());
        entity.setCompatibilityScore(compatibility.getCompatibilityScore());
        entity.setReason(compatibility.getReason());
        return entity;
    }
}