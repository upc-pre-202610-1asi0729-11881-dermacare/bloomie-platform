package com.bloomie.platform.productdiscovery.interfaces.rest.resources;

/**
 * REST response resource representing a product–skin-type compatibility evaluation.
 *
 * @param id                 the unique identifier of this compatibility record
 * @param productId          the evaluated product identifier
 * @param skinType           the skin type (e.g. OILY, DRY, SENSITIVE, COMBINATION, NORMAL)
 * @param compatibilityScore AI-generated score from 0 (incompatible) to 100 (highly compatible)
 * @param reason             one-sentence explanation of the score
 */
public record ProductCompatibilityResource(
        Long id,
        Long productId,
        String skinType,
        Integer compatibilityScore,
        String reason
) {}