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

    @Override
    public long count() {
        return productPersistenceRepository.count();
    }

    @Override
    public void saveAll(List<Product> products) {
        var entities = products.stream()
                .map(ProductPersistenceAssembler::toPersistenceFromDomain)
                .toList();
        productPersistenceRepository.saveAll(entities);
    }

    @Override
    public Product save(Product product) {
        var entity = ProductPersistenceAssembler.toPersistenceFromDomain(product);
        var saved  = productPersistenceRepository.save(entity);
        return ProductPersistenceAssembler.toDomainFromPersistence(saved);
    }
}