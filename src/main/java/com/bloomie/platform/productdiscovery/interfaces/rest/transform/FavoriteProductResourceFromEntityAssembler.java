package com.bloomie.platform.productdiscovery.interfaces.rest.transform;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.FavoriteProductResource;

/**
 * Assembler that converts a {@link FavoriteProduct} domain entity into a {@link FavoriteProductResource}.
 */
public final class FavoriteProductResourceFromEntityAssembler {

    private FavoriteProductResourceFromEntityAssembler() {
    }

    public static FavoriteProductResource toResourceFromEntity(FavoriteProduct favoriteProduct) {
        var savedAt = favoriteProduct.getSavedAt() != null
                ? favoriteProduct.getSavedAt().toInstant().toString()
                : null;
        return new FavoriteProductResource(
                favoriteProduct.getId(),
                favoriteProduct.getProductIdValue(),
                favoriteProduct.getUserIdValue(),
                savedAt
        );
    }
}