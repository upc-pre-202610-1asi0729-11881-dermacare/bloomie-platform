package com.bloomie.platform.productdiscovery.interfaces.rest.transform;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.ProductResource;

/**
 * Assembler that converts a {@link Product} domain entity into a {@link ProductResource}.
 */
public final class ProductResourceFromEntityAssembler {

    private ProductResourceFromEntityAssembler() {
    }

    public static ProductResource toResourceFromEntity(Product product) {
        return new ProductResource(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory().name(),
                product.getDescription(),
                product.getBenefits(),
                product.isAiRecommended()
        );
    }
}