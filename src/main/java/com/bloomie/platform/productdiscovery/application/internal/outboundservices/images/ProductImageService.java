package com.bloomie.platform.productdiscovery.application.internal.outboundservices.images;

/**
 * Outbound port for product image retrieval from an external service.
 * The infrastructure layer provides the concrete Open Beauty Facts implementation.
 */
public interface ProductImageService {

    /**
     * Fetches the image URL for a skincare product by name and category.
     *
     * @param productName  the display name of the product to search
     * @param category     the skincare category (CLEANSER, SERUM, etc.)
     * @return the image URL, or a fallback category image if not found
     */
    String fetchImageUrl(String productName, String category);
}