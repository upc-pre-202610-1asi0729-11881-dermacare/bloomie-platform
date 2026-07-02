package com.bloomie.platform.productdiscovery.application.commandservices;

import com.bloomie.platform.productdiscovery.domain.model.commands.GenerateProductCompatibilityCommand;

/**
 * Application service port for product compatibility commands.
 */
public interface ProductCompatibilityCommandService {

    /**
     * Generates and persists an AI-evaluated compatibility record
     * for the product and skin type described in the command.
     *
     * @param command the command containing product details and target skin type
     */
    void handle(GenerateProductCompatibilityCommand command);
}