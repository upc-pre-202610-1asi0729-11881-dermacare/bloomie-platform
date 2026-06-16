package com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.entities;

import com.bloomie.platform.productdiscovery.domain.model.valueobjects.ProductId;
import com.bloomie.platform.productdiscovery.domain.model.valueobjects.UserId;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.converters.ProductIdPersistenceConverter;
import com.bloomie.platform.productdiscovery.infrastructure.persistence.jpa.converters.UserIdPersistenceConverter;
import com.bloomie.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA persistence entity for favorite products.
 */
@Entity
@Table(
        name = "favorite_products",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "user_id"})
)
@Getter
@Setter
@NoArgsConstructor
public class FavoriteProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Convert(converter = ProductIdPersistenceConverter.class)
    @Column(name = "product_id", nullable = false)
    private ProductId productId;

    @Convert(converter = UserIdPersistenceConverter.class)
    @Column(name = "user_id", nullable = false)
    private UserId userId;
}