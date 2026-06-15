package com.bloomie.platform.productdiscovery.domain.model.valueobjects;

/**
 * Value object representing the product identifier.
 *
 * <p>
 * This value object is used to represent the product identifier
 * within the product discovery context.
 * It must be a positive Long value.
 * </p>
 *
 * @param productId The product identifier. It cannot be null or less than 1.
 */
public record ProductId(Long productId) {

    /**
     * Compact constructor for ProductId.
     * Validates that the productId is not null and is greater than 0.
     * @throws IllegalArgumentException if the productId is null or less than 1.
     */
    public ProductId {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId cannot be null or less than 1");
        }
    }
}