package com.bloomie.platform.productdiscovery.domain.model.commands;

/**
 * Command to remove a product from a user's favorites.
 *
 * @param favoriteProductId the unique identifier of the favorite product record to remove
 */
public record RemoveProductFromFavoritesCommand(Long favoriteProductId) {
}