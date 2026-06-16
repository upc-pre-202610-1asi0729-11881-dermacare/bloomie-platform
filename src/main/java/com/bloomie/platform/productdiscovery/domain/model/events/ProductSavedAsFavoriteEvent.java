package com.bloomie.platform.productdiscovery.domain.model.events;

/**
 * Domain event raised when a product is saved as a favorite by a user.
 *
 * @param favoriteProductId the unique identifier assigned to the new favorite product record
 * @param productId         the product identifier that was saved
 * @param userId            the user identifier who saved the product
 */
public record ProductSavedAsFavoriteEvent(Long favoriteProductId, Long productId, Long userId) {
}