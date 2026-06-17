package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.adapters;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductRepository;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.assemblers.ProductPersistenceAssembler;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.repositories.ProductPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter that bridges the product domain repository port with Spring Data JPA.
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductPersistenceRepository productPersistenceRepository;

    public ProductRepositoryImpl(ProductPersistenceRepository productPersistenceRepository) {
        this.productPersistenceRepository = productPersistenceRepository;
    }

    @Override
    public List<Product> findAll() {
        return productPersistenceRepository.findAll().stream()
                .map(ProductPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productPersistenceRepository.findById(id)
                .map(ProductPersistenceAssembler::toDomainFromPersistence);
    }
}