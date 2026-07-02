package com.bloomie.platform.productdiscovery.interfaces.rest.transform;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.ProductCompatibilityResource;

/**
 * Static assembler that converts a {@link ProductCompatibility} domain aggregate
 * into a {@link ProductCompatibilityResource} REST response.
 */
public final class ProductCompatibilityResourceFromEntityAssembler {

    private ProductCompatibilityResourceFromEntityAssembler() {}

    /**
     * Converts a domain aggregate into a REST resource.
     *
     * @param compatibility the domain aggregate to convert
     * @return the REST response resource
     */
    public static ProductCompatibilityResource toResourceFromEntity(ProductCompatibility compatibility) {
        return new ProductCompatibilityResource(
                compatibility.getId(),
                compatibility.getProductId(),
                compatibility.getSkinType(),
                compatibility.getCompatibilityScore(),
                compatibility.getReason()
        );
    }
}