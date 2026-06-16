package com.bloomie.platform.productdiscovery.interfaces.rest.transform;

import com.bloomie.platform.productdiscovery.domain.model.commands.SaveProductAsFavoriteCommand;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.SaveFavoriteProductResource;

/**
 * Assembler that converts a {@link SaveFavoriteProductResource} into a {@link SaveProductAsFavoriteCommand}.
 */
public final class SaveProductAsFavoriteCommandFromResourceAssembler {

    private SaveProductAsFavoriteCommandFromResourceAssembler() {
    }

    public static SaveProductAsFavoriteCommand toCommandFromResource(SaveFavoriteProductResource resource) {
        return new SaveProductAsFavoriteCommand(resource.productId(), resource.userId());
    }
}