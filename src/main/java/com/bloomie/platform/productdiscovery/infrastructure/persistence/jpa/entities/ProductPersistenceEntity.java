package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductCategory;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA persistence entity for products.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_benefits", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "benefit", nullable = false)
    private List<String> benefits = new ArrayList<>();

    @Column(name = "ai_recommended", nullable = false)
    private boolean aiRecommended;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}