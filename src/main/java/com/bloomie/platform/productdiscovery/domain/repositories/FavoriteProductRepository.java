package com.bloomie.platform.productdiscovery.domain.repositories;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Product discovery favorite product repository port.
 */
public interface FavoriteProductRepository {

    Optional<FavoriteProduct> findById(Long id);

    List<FavoriteProduct> findAllByUserId(UserId userId);

    FavoriteProduct save(FavoriteProduct favoriteProduct);

    boolean existsByProductIdAndUserId(ProductId productId, UserId userId);
}