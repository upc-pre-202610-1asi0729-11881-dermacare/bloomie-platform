package com.bloomie.platform.productdiscovery.application.queryservices;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetFavoriteProductsByUserIdQuery;

import java.util.List;

/**
 * Application service contract for queries over favorite products.
 */
public interface FavoriteProductQueryService {

    /**
     * Retrieves all favorite products saved by a user.
     *
     * @param query query containing the userId
     * @return list of favorite products for the user
     */
    List<FavoriteProduct> handle(GetFavoriteProductsByUserIdQuery query);
}