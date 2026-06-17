package com.bloomie.platform.productdiscovery.interfaces.rest;

import com.bloomie.platform.productdiscovery.application.queryservices.ProductQueryService;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetAllProductsQuery;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetProductByIdQuery;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.ProductResource;
import com.bloomie.platform.productdiscovery.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for product catalog endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product discovery product catalog endpoints")
public class ProductController {

    private final ProductQueryService productQueryService;

    public ProductController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    /**
     * Get all products in the catalog.
     *
     * @return a list of product resources
     */
    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieves all skincare products available in the catalog."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductResource.class)))
            )
    })
    public ResponseEntity<List<ProductResource>> getAllProducts() {
        var query = new GetAllProductsQuery();
        var products = productQueryService.handle(query);
        if (products.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = products.stream()
                .map(ProductResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}