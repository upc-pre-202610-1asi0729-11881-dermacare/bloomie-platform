package com.bloomie.platform.productdiscovery.application.commandservices;

import com.bloomie.platform.productdiscovery.domain.model.commands.SeedProductsCommand;

/**
 * Application service contract for commands over the {@code Product} catalog.
 */
public interface ProductCommandService {

    /**
     * Seeds the product catalog with initial data if it is empty.
     *
     * @param command seed trigger command
     */
    void handle(SeedProductsCommand command);
}
