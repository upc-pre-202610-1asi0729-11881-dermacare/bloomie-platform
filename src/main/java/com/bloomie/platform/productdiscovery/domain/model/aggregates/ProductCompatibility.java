package com.bloomie.platform.productdiscovery.domain.model.aggregates;

import com.bloomie.platform.productdiscovery.domain.model.events.ProductCompatibilityEvaluatedEvent;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * ProductCompatibility aggregate root.
 *
 * <p>Represents the compatibility evaluation between a skincare product
 * and a specific skin type, including an AI-generated score and rationale.</p>
 */
@Getter
public class ProductCompatibility extends AbstractDomainAggregateRoot<ProductCompatibility> {

    /**
     * The unique identifier of this compatibility record.
     */
    @Setter
    private Long id;

    /**
     * The identifier of the product being evaluated.
     */
    @Setter
    private Long productId;

    /**
     * The skin type this compatibility evaluation targets
     * (e.g. OILY, DRY, SENSITIVE, COMBINATION, NORMAL).
     */
    @Setter
    private String skinType;

    /**
     * AI-generated compatibility score from 0 (incompatible) to 100 (highly compatible).
     */
    @Setter
    private Integer compatibilityScore;

    /**
     * One-sentence explanation of why this product is or is not compatible with the skin type.
     */
    @Setter
    private String reason;

    /**
     * Default constructor required for reconstruction from persistence.
     */
    public ProductCompatibility() {}

    /**
     * Creates a new ProductCompatibility with all required fields except the database id.
     *
     * @param productId          the product being evaluated
     * @param skinType           the target skin type
     * @param compatibilityScore AI-generated score (0–100)
     * @param reason             one-sentence rationale for the score
     */
    public ProductCompatibility(Long productId, String skinType, Integer compatibilityScore, String reason) {
        this.productId = productId;
        this.skinType = skinType;
        this.compatibilityScore = compatibilityScore;
        this.reason = reason;
    }

    /**
     * Registers the domain event for when this compatibility evaluation is generated.
     * Called by the repository after the aggregate is persisted and has an assigned id.
     */
    public void onEvaluated() {
        registerDomainEvent(new ProductCompatibilityEvaluatedEvent(this.id, this.productId, this.skinType, this.compatibilityScore));
    }
}