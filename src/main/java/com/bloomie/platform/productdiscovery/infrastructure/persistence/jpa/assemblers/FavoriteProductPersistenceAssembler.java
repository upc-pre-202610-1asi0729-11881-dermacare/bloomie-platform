package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities.FavoriteProductPersistenceEntity;

/**
 * Static assembler between favorite product domain and persistence representations.
 */
public final class FavoriteProductPersistenceAssembler {

    private FavoriteProductPersistenceAssembler() {
    }

    public static FavoriteProduct toDomainFromPersistence(FavoriteProductPersistenceEntity entity) {
        if (entity == null) return null;
        var favoriteProduct = new FavoriteProduct();
        favoriteProduct.setId(entity.getId());
        favoriteProduct.setProductId(entity.getProductId());
        favoriteProduct.setUserId(entity.getUserId());
        return favoriteProduct;
    }

    public static FavoriteProductPersistenceEntity toPersistenceFromDomain(FavoriteProduct favoriteProduct) {
        if (favoriteProduct == null) return null;
        var entity = new FavoriteProductPersistenceEntity();
        if (favoriteProduct.getId() != null) {
            entity.setId(favoriteProduct.getId());
        }
        entity.setProductId(favoriteProduct.getProductId());
        entity.setUserId(favoriteProduct.getUserId());
        return entity;
    }
}