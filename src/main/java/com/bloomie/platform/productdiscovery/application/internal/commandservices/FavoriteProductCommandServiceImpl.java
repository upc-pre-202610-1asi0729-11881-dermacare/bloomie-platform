package com.bloomie.platform.productdiscovery.application.internal.commandservices;

import com.bloomie.platform.productdiscovery.application.commandservices.FavoriteProductCommandService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.commands.RemoveProductFromFavoritesCommand;
import com.bloomie.platform.productdiscovery.domain.model.commands.SaveProductAsFavoriteCommand;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.domain.repositories.FavoriteProductRepository;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

/**
 * Application service that executes favorite product commands.
 */
@Service
public class FavoriteProductCommandServiceImpl implements FavoriteProductCommandService {

    private final FavoriteProductRepository favoriteProductRepository;

    public FavoriteProductCommandServiceImpl(FavoriteProductRepository favoriteProductRepository) {
        this.favoriteProductRepository = favoriteProductRepository;
    }

    @Override
    public Result<FavoriteProduct, ApplicationError> handle(SaveProductAsFavoriteCommand command) {
        var productId = new ProductId(command.productId());
        var userId = new UserId(command.userId());

        if (favoriteProductRepository.existsByProductIdAndUserId(productId, userId)) {
            return Result.failure(ApplicationError.conflict(
                    "FavoriteProduct",
                    "Product %d is already saved as favorite by user %d"
                            .formatted(command.productId(), command.userId())
            ));
        }

        try {
            var favoriteProduct = new FavoriteProduct(command.productId(), command.userId());
            favoriteProduct = favoriteProductRepository.save(favoriteProduct);
            return Result.success(favoriteProduct);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("save-product-as-favorite", e.getMessage()));
        }
    }
}