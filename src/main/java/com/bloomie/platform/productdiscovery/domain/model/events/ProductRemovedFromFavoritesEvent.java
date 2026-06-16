package com.bloomie.platform.productdiscovery.domain.model.events;

/**
 * Domain event raised when a product is removed from a user's favorites.
 *
 * @param favoriteProductId the unique identifier of the removed favorite product record
 * @param productId         the product identifier that was removed
 * @param userId            the user identifier who removed the product
 */
public record ProductRemovedFromFavoritesEvent(Long favoriteProductId, Long productId, Long userId) {
}
