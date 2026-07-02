package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities.ProductCompatibilityPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ProductCompatibilityPersistenceEntity}.
 */
public interface ProductCompatibilityPersistenceRepository
        extends JpaRepository<ProductCompatibilityPersistenceEntity, Long> {

    /**
     * Finds all compatibility records associated with a given product.
     *
     * @param productId the product identifier
     * @return list of matching entities, may be empty
     */
    List<ProductCompatibilityPersistenceEntity> findByProductId(Long productId);

    /**
     * Finds all compatibility records associated with a given skin type.
     *
     * @param skinType the skin type string
     * @return list of matching entities, may be empty
     */
    List<ProductCompatibilityPersistenceEntity> findBySkinType(String skinType);
}