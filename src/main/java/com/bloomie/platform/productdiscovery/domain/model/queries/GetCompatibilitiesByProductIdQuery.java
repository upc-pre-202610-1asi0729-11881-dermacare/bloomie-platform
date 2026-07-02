package com.bloomie.platform.productdiscovery.domain.model.queries;

/**
 * Query to retrieve all compatibility evaluations for a specific product.
 *
 * @param productId the unique identifier of the product
 */
public record GetCompatibilitiesByProductIdQuery(Long productId) {}