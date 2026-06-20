package com.bloomie.platform.productdiscovery.domain.model.queries;

/**
 * Query to retrieve all favorite products saved by a user.
 *
 * @param userId the user identifier whose favorites are requested
 */
public record GetFavoriteProductsByUserIdQuery(Long userId) {
}