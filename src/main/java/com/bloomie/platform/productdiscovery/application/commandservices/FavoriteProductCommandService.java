package com.bloomie.platform.productdiscovery.application.commandservices;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.commands.RemoveProductFromFavoritesCommand;
import com.bloomie.platform.productdiscovery.domain.model.commands.SaveProductAsFavoriteCommand;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;

/**
 * Application service contract for commands over favorite products.
 */
public interface FavoriteProductCommandService {

    /**
     * Handles saving a product as a favorite for a user.
     *
     * @param command command containing productId and userId
     * @return the saved favorite product or an application error
     */
    Result<FavoriteProduct, ApplicationError> handle(SaveProductAsFavoriteCommand command);

    /**
     * Handles removing a product from a user's favorites.
     *
     * @param command command containing the favoriteProductId to remove
     * @return the removed favorite product or an application error
     */
    Result<FavoriteProduct, ApplicationError> handle(RemoveProductFromFavoritesCommand command);
}