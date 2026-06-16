package com.bloomie.platform.productdiscovery.interfaces.rest.resources;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Resource representing the request body for saving a product as a favorite.
 *
 * @param productId the product identifier to save as favorite
 * @param userId    the user identifier who saves the product
 */
public record SaveFavoriteProductResource(
        @NotNull @Min(1) Long productId,
        @NotNull @Min(1) Long userId
) {
}