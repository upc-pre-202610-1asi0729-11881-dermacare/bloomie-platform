package com.bloomie.platform.productdiscovery.domain.model.aggregates;

import com.bloomie.platform.productdiscovery.domain.model.events.ProductRemovedFromFavoritesEvent;
import com.bloomie.platform.productdiscovery.domain.model.events.ProductSavedAsFavoriteEvent;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * FavoriteProduct aggregate root.
 *
 * <p>
 * Represents a product saved as a favorite by a user
 * within the product discovery context.
 * </p>
 */
@Getter
public class FavoriteProduct extends AbstractDomainAggregateRoot<FavoriteProduct> {

    /**
     * The unique identifier for the favorite product.
     */
    @Setter
    private Long id;

    /**
     * The product identifier associated with the favorite product.
     */
    @Setter
    private ProductId productId;

    /**
     * The user identifier associated with the favorite product.
     */
    @Setter
    private UserId userId;

    /**
     * The date and time when the product was saved as a favorite.
     */
    @Setter
    private Date savedAt;

    /**
     * Default constructor for FavoriteProduct.
     * Required by JPA.
     */
    public FavoriteProduct() {}

    /**
     * Constructor for FavoriteProduct with productId and userId.
     * @param productId The product identifier value.
     * @param userId The user identifier value.
     */
    public FavoriteProduct(Long productId, Long userId) {
        this();
        this.productId = new ProductId(productId);
        this.userId = new UserId(userId);
    }

    /**
     * Gets the product identifier value.
     * @return The product id as a Long.
     */
    public Long getProductIdValue() {
        return this.productId.productId();
    }

    /**
     * Gets the user identifier value.
     * @return The user id as a Long.
     */
    public Long getUserIdValue() {
        return this.userId.userId();
    }

    /**
     * Registers the domain event for when this product is saved as a favorite.
     * Called by the repository after the aggregate is persisted and has an assigned id.
     */
    public void onSavedAsFavorite() {
        registerDomainEvent(new ProductSavedAsFavoriteEvent(this.id, this.productId.productId(), this.userId.userId()));
    }

    /**
     * Registers the domain event for when this product is removed from favorites.
     * Called by the command service before deletion.
     */
    public void removeFromFavorites() {
        registerDomainEvent(new ProductRemovedFromFavoritesEvent(this.id, this.productId.productId(), this.userId.userId()));
    }
}