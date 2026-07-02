package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for product–skin-type compatibility evaluations.
 */
@Entity
@Table(name = "product_compatibilities")
@Getter
@Setter
@NoArgsConstructor
public class ProductCompatibilityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    /**
     * Foreign key to the evaluated product.
     */
    @Column(name = "product_id", nullable = false)
    private Long productId;

    /**
     * The skin type this evaluation applies to (e.g. OILY, DRY, SENSITIVE).
     */
    @Column(name = "skin_type", nullable = false)
    private String skinType;

    /**
     * AI-generated compatibility score from 0 (incompatible) to 100 (highly compatible).
     */
    @Column(name = "compatibility_score", nullable = false)
    private Integer compatibilityScore;

    /**
     * One-sentence explanation of the compatibility evaluation.
     */
    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;
}