package com.bloomie.platform.productdiscovery.interfaces.rest.resources;

import java.util.List;

/**
 * Resource representing a skincare product in REST responses.
 *
 * @param id            the unique identifier of the product
 * @param name          the display name of the product
 * @param brand         the brand that manufactures the product
 * @param category      the skincare category of the product
 * @param description   the detailed description of the product
 * @param benefits      the list of key benefits provided by the product
 * @param aiRecommended whether this product has been flagged as AI-recommended
 */
public record ProductResource(
        Long id,
        String name,
        String brand,
        String category,
        String description,
        List<String> benefits,
        boolean aiRecommended,
        String imageUrl
) {}