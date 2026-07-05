package com.bloomie.platform.productdiscovery.application.queryservices;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesByProductIdQuery;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesBySkinTypeQuery;

import java.util.List;

/**
 * Application service port for product compatibility queries.
 */
public interface ProductCompatibilityQueryService {

    /**
     * Returns all compatibility evaluations for the product identified in the query.
     *
     * @param query the query containing the product id
     * @return list of compatibility aggregates, may be empty
     */
    List<ProductCompatibility> handle(GetCompatibilitiesByProductIdQuery query);

    /**
     * Returns all compatibility evaluations for the skin type identified in the query.
     *
     * @param query the query containing the skin type
     * @return list of compatibility aggregates, may be empty
     */
    List<ProductCompatibility> handle(GetCompatibilitiesBySkinTypeQuery query);
}