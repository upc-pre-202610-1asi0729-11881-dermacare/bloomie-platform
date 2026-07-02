package com.bloomie.platform.productdiscovery.domain.repositories;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;

import java.util.List;

/**
 * Domain repository port for {@link ProductCompatibility} persistence operations.
 *
 * <p>Infrastructure adapters implement this interface to decouple the domain
 * from any specific persistence technology.</p>
 */
public interface ProductCompatibilityRepository {

    /**
     * Persists a single product compatibility aggregate.
     *
     * @param compatibility the compatibility to save
     * @return the saved compatibility with its assigned id
     */
    ProductCompatibility save(ProductCompatibility compatibility);

    /**
     * Persists a collection of product compatibility aggregates in bulk.
     *
     * @param compatibilities the list of compatibilities to save
     */
    void saveAll(List<ProductCompatibility> compatibilities);

    /**
     * Returns all compatibility evaluations for a given product.
     *
     * @param productId the product identifier
     * @return list of compatibilities, may be empty
     */
    List<ProductCompatibility> findByProductId(Long productId);

    /**
     * Returns all compatibility evaluations for a given skin type.
     *
     * @param skinType the skin type string (e.g. OILY, DRY)
     * @return list of compatibilities, may be empty
     */
    List<ProductCompatibility> findBySkinType(String skinType);

    /**
     * Returns the total number of persisted compatibility evaluations.
     *
     * @return total count
     */
    long count();
}