package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories;

import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities.FavoriteProductPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for favorite product persistence entities.
 */
@Repository
public interface FavoriteProductPersistenceRepository extends JpaRepository<FavoriteProductPersistenceEntity, Long> {

    List<FavoriteProductPersistenceEntity> findAllByUserId(UserId userId);

    boolean existsByProductIdAndUserId(ProductId productId, UserId userId);
}