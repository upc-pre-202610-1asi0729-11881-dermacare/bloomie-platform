package com.bloomie.platform.productdiscovery.domain.model.commands;

/**
 * Save Product As Favorite Command.
 * @param productId the product identifier to save as favorite.
 *                  Cannot be null or negative.
 * @param userId the user identifier who saves the product.
 *               Cannot be null or negative.
 */
public record SaveProductAsFavoriteCommand(Long productId, Long userId) {

    /**
     * Compact constructor with validation.
     * @throws IllegalArgumentException if productId is null or negative.
     * @throws IllegalArgumentException if userId is null or negative.
     */

    public SaveProductAsFavoriteCommand {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId cannot be null or negative");
        }
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("userId cannot be null or negative");
        }
    }
}
