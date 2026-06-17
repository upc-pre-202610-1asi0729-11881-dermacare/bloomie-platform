package com.bloomie.platform.productdiscovery.application.queryservices;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetAllProductsQuery;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetProductByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for product read queries.
 */
public interface ProductQueryService {

    /**
     * Handles retrieval of all products in the catalog.
     *
     * @param query query marker
     * @return list of all products
     * @see GetAllProductsQuery
     */
    List<Product> handle(GetAllProductsQuery query);

    /**
     * Handles retrieval of a product by its unique identifier.
     *
     * @param query product-id query
     * @return matching product, if found
     * @see GetProductByIdQuery
     */
    Optional<Product> handle(GetProductByIdQuery query);
}