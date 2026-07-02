package com.bloomie.platform.productdiscovery.application.internal.outboundservices.compatibility;

/**
 * Outbound port for AI-powered product-skin compatibility evaluation.
 *
 * <p>Infrastructure adapters implement this interface to provide the actual
 * AI integration without leaking any provider-specific concerns into
 * the application or domain layers.</p>
 */
public interface ProductCompatibilityAiService {

    /**
     * Evaluates the compatibility between a skincare product and a skin type.
     *
     * @param productName the display name of the product
     * @param category    the skincare category (e.g. CLEANSER, SERUM)
     * @param skinType    the target skin type (e.g. OILY, DRY, SENSITIVE)
     * @return a {@link ProductCompatibilityResult} containing the score and one-sentence reason
     */
    ProductCompatibilityResult evaluateCompatibility(String productName, String category, String skinType);

    /**
     * AI evaluation result carrying the compatibility score and rationale.
     *
     * @param score  compatibility score from 0 (incompatible) to 100 (highly compatible)
     * @param reason one-sentence explanation of the score, under 100 characters
     */
    record ProductCompatibilityResult(int score, String reason) {}
}