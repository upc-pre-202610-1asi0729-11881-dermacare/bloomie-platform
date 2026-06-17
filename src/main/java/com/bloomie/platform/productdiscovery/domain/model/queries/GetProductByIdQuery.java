package com.bloomie.platform.productdiscovery.domain.model.queries;

/**
 * Query to get a product by its unique identifier.
 *
 * @param productId the product identifier. Cannot be null or less than 1.
 */
public record GetProductByIdQuery(Long productId) {

    public GetProductByIdQuery {
        if (productId == null || productId < 1)
            throw new IllegalArgumentException("productId cannot be null or less than 1");
    }
}
