package com.bloomie.platform.productdiscovery.domain.repositories;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;

import java.util.List;
import java.util.Optional;


/**
 * Product discovery product repository port.
 */
public interface ProductRepository {

    /**
     * Retrieves all products in the catalog.
     *
     * @return list of all products
     */
    List<Product> findAll();

    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the product identifier
     * @return the matching product, if found
     */
    Optional<Product> findById(Long id);

    /**
     * Returns the number of products currently in the catalog.
     *
     * @return total product count
     */
    long count();

    /**
     * Persists a collection of products (bulk insert for seeding).
     *
     * @param products the products to save
     */
    void saveAll(List<Product> products);
}