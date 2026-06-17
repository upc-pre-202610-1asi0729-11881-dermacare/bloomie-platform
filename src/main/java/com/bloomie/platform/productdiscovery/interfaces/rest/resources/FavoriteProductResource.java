package com.bloomie.platform.productdiscovery.interfaces.rest.resources;

/**
 * Resource representing a favorite product in REST responses.
 *
 * @param id        the unique identifier of the favorite product record
 * @param productId the product identifier
 * @param userId    the user identifier who saved the product
 * @param savedAt   the ISO 8601 date-time string for when the product was saved as a favorite
 */
public record FavoriteProductResource(Long id, Long productId, Long userId, String savedAt) {
}