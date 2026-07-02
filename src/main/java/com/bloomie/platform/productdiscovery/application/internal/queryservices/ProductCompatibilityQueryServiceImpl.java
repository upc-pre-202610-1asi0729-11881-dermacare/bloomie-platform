package com.bloomie.platform.productdiscovery.application.internal.queryservices;

import com.bloomie.platform.productdiscovery.application.queryservices.ProductCompatibilityQueryService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesByProductIdQuery;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesBySkinTypeQuery;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductCompatibilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that handles product compatibility query operations.
 */
@Service
public class ProductCompatibilityQueryServiceImpl implements ProductCompatibilityQueryService {

    private final ProductCompatibilityRepository productCompatibilityRepository;

    /**
     * Constructs the query service with its required repository.
     *
     * @param productCompatibilityRepository domain repository port for read operations
     */
    public ProductCompatibilityQueryServiceImpl(ProductCompatibilityRepository productCompatibilityRepository) {
        this.productCompatibilityRepository = productCompatibilityRepository;
    }

    /**
     * Returns all compatibility evaluations for the product specified in the query.
     *
     * @param query the query containing the product id
     * @return list of compatibility aggregates, may be empty
     */
    @Override
    public List<ProductCompatibility> handle(GetCompatibilitiesByProductIdQuery query) {
        return productCompatibilityRepository.findByProductId(query.productId());
    }

    /**
     * Returns all compatibility evaluations for the skin type specified in the query.
     *
     * @param query the query containing the skin type
     * @return list of compatibility aggregates, may be empty
     */
    @Override
    public List<ProductCompatibility> handle(GetCompatibilitiesBySkinTypeQuery query) {
        return productCompatibilityRepository.findBySkinType(query.skinType());
    }
}