package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductCompatibilityRepository;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers.ProductCompatibilityPersistenceAssembler;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories.ProductCompatibilityPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository adapter that bridges the {@link ProductCompatibilityRepository} domain port
 * with the Spring Data JPA {@link ProductCompatibilityPersistenceRepository}.
 */
@Repository
public class ProductCompatibilityRepositoryImpl implements ProductCompatibilityRepository {

    private final ProductCompatibilityPersistenceRepository productCompatibilityPersistenceRepository;

    /**
     * Constructs the adapter with its required Spring Data repository.
     *
     * @param productCompatibilityPersistenceRepository the underlying JPA repository
     */
    public ProductCompatibilityRepositoryImpl(
            ProductCompatibilityPersistenceRepository productCompatibilityPersistenceRepository) {
        this.productCompatibilityPersistenceRepository = productCompatibilityPersistenceRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProductCompatibility save(ProductCompatibility compatibility) {
        var entity = ProductCompatibilityPersistenceAssembler.toPersistenceFromDomain(compatibility);
        var saved = productCompatibilityPersistenceRepository.save(entity);
        return ProductCompatibilityPersistenceAssembler.toDomainFromPersistence(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(List<ProductCompatibility> compatibilities) {
        var entities = compatibilities.stream()
                .map(ProductCompatibilityPersistenceAssembler::toPersistenceFromDomain)
                .toList();
        productCompatibilityPersistenceRepository.saveAll(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductCompatibility> findByProductId(Long productId) {
        return productCompatibilityPersistenceRepository.findByProductId(productId).stream()
                .map(ProductCompatibilityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProductCompatibility> findBySkinType(String skinType) {
        return productCompatibilityPersistenceRepository.findBySkinType(skinType).stream()
                .map(ProductCompatibilityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return productCompatibilityPersistenceRepository.count();
    }
}