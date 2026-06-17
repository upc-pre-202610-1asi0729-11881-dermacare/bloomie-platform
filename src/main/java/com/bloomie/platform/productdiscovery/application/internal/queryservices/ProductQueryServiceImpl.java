package com.bloomie.platform.productdiscovery.application.internal.queryservices;

import com.bloomie.platform.productdiscovery.application.queryservices.ProductQueryService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetAllProductsQuery;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that resolves product read queries.
 */
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        return productRepository.findAll();
    }
}