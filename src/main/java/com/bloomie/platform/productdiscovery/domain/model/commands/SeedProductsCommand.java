package com.bloomie.platform.productdiscovery.domain.model.commands;

/**
 * Command that triggers the seeding of the initial product catalog.
 * Only executed when the products table is empty.
 */
public record SeedProductsCommand() {
}
