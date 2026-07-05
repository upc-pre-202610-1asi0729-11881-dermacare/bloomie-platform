package com.bloomie.platform.productdiscovery.domain.model.commands;

/**
 * Command to request an AI-generated compatibility evaluation
 * for a given product and skin type combination.
 *
 * @param productId   the unique identifier of the product
 * @param productName the display name of the product, sent to the AI
 * @param category    the skincare category (e.g. CLEANSER, SERUM)
 * @param skinType    the target skin type (e.g. OILY, DRY, SENSITIVE)
 */
public record GenerateProductCompatibilityCommand(
        Long productId,
        String productName,
        String category,
        String skinType
) {}