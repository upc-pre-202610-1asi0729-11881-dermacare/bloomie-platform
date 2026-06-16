package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.domain.repositories.FavoriteProductRepository;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers.FavoriteProductPersistenceAssembler;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories.FavoriteProductPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the favorite product domain repository port with Spring Data JPA.
 */
@Repository
public class FavoriteProductRepositoryImpl implements FavoriteProductRepository {

    private final FavoriteProductPersistenceRepository persistenceRepository;

    public FavoriteProductRepositoryImpl(FavoriteProductPersistenceRepository persistenceRepository) {
        this.persistenceRepository = persistenceRepository;
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
        var saved = persistenceRepository.save(
                FavoriteProductPersistenceAssembler.toPersistenceFromDomain(favoriteProduct));
        return FavoriteProductPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsByProductIdAndUserId(ProductId productId, UserId userId) {
        return persistenceRepository.existsByProductIdAndUserId(productId, userId);
    }
}