package com.bloomie.platform.productdiscovery.domain.model.events;

/**
 * Domain event raised when a product-skin-type compatibility evaluation is generated.
 *
 * @param compatibilityId    the unique identifier assigned to the new compatibility record
 * @param productId          the product identifier that was evaluated
 * @param skinType           the target skin type used for the evaluation
 * @param compatibilityScore the resulting compatibility score (0-100)
 */
public record ProductCompatibilityEvaluatedEvent(
        Long compatibilityId,
        Long productId,
        String skinType,
        Integer compatibilityScore
) {
}
