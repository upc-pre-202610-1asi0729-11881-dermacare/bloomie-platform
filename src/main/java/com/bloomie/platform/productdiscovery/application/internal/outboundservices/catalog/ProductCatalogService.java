package com.bloomie.platform.productdiscovery.application.internal.outboundservices.catalog;

import com.bloomie.platform.productdiscovery.domain.model.aggregates.Product;
import java.util.List;

/**
 * Outbound port for fetching skincare products from an external catalog service.
 * The infrastructure layer provides the concrete Open Beauty Facts implementation.
 */
public interface ProductCatalogService {

    /**
     * Fetches skincare products for a given category from an external source.
     *
     * @param searchTerm search terms to find products for the category
     * @param category   the product category (CLEANSER, TONER, SERUM, MOISTURIZER, SUNSCREEN)
     * @param limit      maximum number of products to fetch
     * @return list of products with names, brands, descriptions and images
     */
    List<Product> fetchProductsByCategory(String searchTerm, String category, int limit);
}