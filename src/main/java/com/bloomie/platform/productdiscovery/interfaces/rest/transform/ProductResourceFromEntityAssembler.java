package com.bloomie.platform.productdiscovery.interfaces.rest.transform;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.ProductResource;

public class ProductResourceFromEntityAssembler {
    public static ProductResource toResourceFromEntity(Product product) {
        return new ProductResource(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getCategory().name(),
                product.getDescription(),
                product.getBenefits(),
                product.isAiRecommended(),
                product.getImageUrl()
        );
    }
}
