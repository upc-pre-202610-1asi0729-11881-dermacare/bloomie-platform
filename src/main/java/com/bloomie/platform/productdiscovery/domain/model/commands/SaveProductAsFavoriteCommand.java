package com.bloomie.platform.productdiscovery.domain.model.commands;

/**
 * Save Product As Favorite Command.
 * @param productId the product identifier to save as favorite.
 * @param userId the user identifier who saves the product.
 */
public record SaveProductAsFavoriteCommand(Long productId, Long userId) {
}