package com.bloomie.platform.productdiscovery.domain.model.aggregates;

import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductCategory;
import com.bloomie.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Product aggregate root.
 *
 * <p>
 * Represents a skincare product available in the product discovery catalog.
 * </p>
 */
@Getter
public class Product extends AbstractDomainAggregateRoot<Product> {

    /**
     * The unique identifier for the product.
     */
    @Setter
    private Long id;

    /**
     * The display name of the product.
     */
    @Setter
    private String name;

    /**
     * The brand that manufactures the product.
     */
    @Setter
    private String brand;

    /**
     * The skincare category this product belongs to.
     */
    @Setter
    private ProductCategory category;

    /**
     * The detailed description of the product and its purpose.
     */
    @Setter
    private String description;

    /**
     * The list of key benefits provided by the product.
     */
    @Setter
    private List<String> benefits;

    /**
     * Whether this product has been flagged as AI-recommended.
     */
    @Setter
    private boolean aiRecommended;

    @Setter
    private String imageUrl;

    /**
     * Default constructor for Product.
     * Required for reconstruction from persistence.
     */
    public Product() {
        this.benefits = new ArrayList<>();
        this.imageUrl = "";
    }}