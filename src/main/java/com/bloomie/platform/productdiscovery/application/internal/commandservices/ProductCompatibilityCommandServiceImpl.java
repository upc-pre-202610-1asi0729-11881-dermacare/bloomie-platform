package com.bloomie.platform.productdiscovery.application.internal.commandservices;

import com.bloomie.platform.productdiscovery.application.commandservices.ProductCompatibilityCommandService;
import com.bloomie.platform.productdiscovery.application.internal.outboundservices.compatibility.ProductCompatibilityAiService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.model.commands.GenerateProductCompatibilityCommand;
import com.bloomie.platform.productdiscovery.domain.repositories.ProductCompatibilityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service that handles {@link GenerateProductCompatibilityCommand}.
 *
 * <p>Delegates AI evaluation to the {@link ProductCompatibilityAiService} outbound port
 * and persists the result via the {@link ProductCompatibilityRepository} domain port.</p>
 */
@Service
@Slf4j
public class ProductCompatibilityCommandServiceImpl implements ProductCompatibilityCommandService {

    private final ProductCompatibilityRepository productCompatibilityRepository;
    private final ProductCompatibilityAiService productCompatibilityAiService;

    /**
     * Constructs the service with its required dependencies.
     *
     * @param productCompatibilityRepository domain repository port for persistence
     * @param productCompatibilityAiService  outbound port for AI evaluation
     */
    public ProductCompatibilityCommandServiceImpl(
            ProductCompatibilityRepository productCompatibilityRepository,
            ProductCompatibilityAiService productCompatibilityAiService) {
        this.productCompatibilityRepository = productCompatibilityRepository;
        this.productCompatibilityAiService = productCompatibilityAiService;
    }

    /**
     * Generates an AI compatibility score for the given product and skin type,
     * then persists the result as a {@link ProductCompatibility} aggregate.
     *
     * @param command the command containing product details and target skin type
     */
    @Override
    public void handle(GenerateProductCompatibilityCommand command) {
        log.info("Generating compatibility for product '{}' and skin type '{}'",
                command.productName(), command.skinType());

        var result = productCompatibilityAiService.evaluateCompatibility(
                command.productName(), command.category(), command.skinType());

        var compatibility = new ProductCompatibility(
                command.productId(),
                command.skinType(),
                result.score(),
                result.reason()
        );

        productCompatibilityRepository.save(compatibility);

        log.info("Saved compatibility for product {} / {}: score={}",
                command.productId(), command.skinType(), result.score());
    }
}