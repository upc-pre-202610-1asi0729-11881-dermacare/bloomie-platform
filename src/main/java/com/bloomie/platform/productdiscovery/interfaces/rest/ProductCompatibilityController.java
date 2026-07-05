package com.bloomie.platform.productdiscovery.interfaces.rest;

import com.bloomie.platform.productdiscovery.application.queryservices.ProductCompatibilityQueryService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.ProductCompatibility;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesByProductIdQuery;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetCompatibilitiesBySkinTypeQuery;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.ProductCompatibilityResource;
import com.bloomie.platform.productdiscovery.interfaces.rest.transform.ProductCompatibilityResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for product–skin-type compatibility endpoints.
 *
 * <p>Accepts an optional {@code productId} or {@code skinType} query parameter
 * and dispatches to the appropriate query handler.</p>
 */
@RestController
@RequestMapping(value = "/api/v1/product-compatibilities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Product Compatibilities", description = "Product discovery skin compatibility endpoints")
public class ProductCompatibilityController {

    private final ProductCompatibilityQueryService productCompatibilityQueryService;

    /**
     * Constructs the controller with its required query service.
     *
     * @param productCompatibilityQueryService the application query service
     */
    public ProductCompatibilityController(ProductCompatibilityQueryService productCompatibilityQueryService) {
        this.productCompatibilityQueryService = productCompatibilityQueryService;
    }

    /**
     * Retrieves compatibility evaluations filtered by product or skin type.
     *
     * <p>If {@code productId} is provided, returns all evaluations for that product.
     * If {@code skinType} is provided instead, returns all evaluations for that skin type.
     * If neither is provided, returns an empty list.</p>
     *
     * @param productId the product identifier (optional)
     * @param skinType  the skin type string (optional)
     * @return list of compatibility resources
     */
    @GetMapping
    @Operation(
            summary = "Get product compatibilities",
            description = "Retrieves compatibility evaluations filtered by productId or skinType."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Compatibilities retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductCompatibilityResource.class)))
            )
    })
    public ResponseEntity<List<ProductCompatibilityResource>> getCompatibilities(
            @RequestParam(required = false)
            @Parameter(description = "Filter by product identifier", example = "1")
            Long productId,

            @RequestParam(required = false)
            @Parameter(description = "Filter by skin type (OILY, DRY, SENSITIVE, COMBINATION, NORMAL)", example = "OILY")
            String skinType
    ) {
        if (productId != null) {
            var results = productCompatibilityQueryService.handle(new GetCompatibilitiesByProductIdQuery(productId));
            return ResponseEntity.ok(toResources(results));
        }

        if (skinType != null) {
            var results = productCompatibilityQueryService.handle(new GetCompatibilitiesBySkinTypeQuery(skinType));
            return ResponseEntity.ok(toResources(results));
        }

        return ResponseEntity.ok(Collections.emptyList());
    }

    private List<ProductCompatibilityResource> toResources(List<ProductCompatibility> compatibilities) {
        if (compatibilities.isEmpty()) return Collections.emptyList();
        return compatibilities.stream()
                .map(ProductCompatibilityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}