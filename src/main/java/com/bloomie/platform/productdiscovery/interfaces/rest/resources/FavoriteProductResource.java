package com.bloomie.platform.productdiscovery.interfaces.rest.resources;

/**
 * Resource representing a favorite product in REST responses.
 *
 * @param id        the unique identifier of the favorite product record
 * @param productId the product identifier
 * @param userId    the user identifier who saved the product
 */
public record FavoriteProductResource(Long id, Long productId, Long userId) {
}