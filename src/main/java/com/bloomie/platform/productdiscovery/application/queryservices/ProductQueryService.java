package com.bloomie.platform.productdiscovery.application.queryservices;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetAllProductsQuery;

import java.util.List;

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
}