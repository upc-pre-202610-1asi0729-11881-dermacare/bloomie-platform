package com.bloomie.platform.productdiscovery.application.internal.queryservices;

import com.bloomie.platform.productdiscovery.application.queryservices.FavoriteProductQueryService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetFavoriteProductsByUserIdQuery;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.domain.repositories.FavoriteProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that executes favorite product queries.
 */
@Service
public class FavoriteProductQueryServiceImpl implements FavoriteProductQueryService {

    private final FavoriteProductRepository favoriteProductRepository;

    public FavoriteProductQueryServiceImpl(FavoriteProductRepository favoriteProductRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
    }

    @Override
    public List<FavoriteProduct> handle(GetFavoriteProductsByUserIdQuery query) {
        return favoriteProductRepository.findAllByUserId(new UserId(query.userId()));
    }
}