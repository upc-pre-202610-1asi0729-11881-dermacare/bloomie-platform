package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.domain.repositories.FavoriteProductRepository;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers.FavoriteProductPersistenceAssembler;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories.FavoriteProductPersistenceRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the favorite product domain repository port with Spring Data JPA.
 */
@Repository
public class FavoriteProductRepositoryImpl implements FavoriteProductRepository {

    private final FavoriteProductPersistenceRepository persistenceRepository;
    private final ApplicationEventPublisher eventPublisher;

    public FavoriteProductRepositoryImpl(FavoriteProductPersistenceRepository persistenceRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.persistenceRepository = persistenceRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<FavoriteProduct> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(FavoriteProductPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<FavoriteProduct> findAllByUserId(UserId userId) {
        return persistenceRepository.findAllByUserId(userId).stream()
                .map(FavoriteProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public FavoriteProduct save(FavoriteProduct favoriteProduct) {
        boolean isNew = favoriteProduct.getId() == null;
        var saved = persistenceRepository.save(
                FavoriteProductPersistenceAssembler.toPersistenceFromDomain(favoriteProduct));
        if (isNew) {
            favoriteProduct.setId(saved.getId());
            favoriteProduct.onSavedAsFavorite();
        }
        favoriteProduct.domainEvents().forEach(eventPublisher::publishEvent);
        favoriteProduct.clearDomainEvents();
        return FavoriteProductPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsByProductIdAndUserId(ProductId productId, UserId userId) {
        return persistenceRepository.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    public void delete(FavoriteProduct favoriteProduct) {
        favoriteProduct.domainEvents().forEach(eventPublisher::publishEvent);
        favoriteProduct.clearDomainEvents();
        persistenceRepository.deleteById(favoriteProduct.getId());
    }
}