package com.bloomie.platform.productdiscovery.interfaces.rest;

import com.bloomie.platform.productdiscovery.application.commandservices.FavoriteProductCommandService;
import com.bloomie.platform.productdiscovery.application.queryservices.FavoriteProductQueryService;
import com.bloomie.platform.productdiscovery.domain.model.aggregates.FavoriteProduct;
import com.bloomie.platform.productdiscovery.domain.model.commands.RemoveProductFromFavoritesCommand;
import com.bloomie.platform.productdiscovery.domain.model.queries.GetFavoriteProductsByUserIdQuery;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.FavoriteProductResource;
import com.bloomie.platform.productdiscovery.interfaces.rest.resources.SaveFavoriteProductResource;
import com.bloomie.platform.productdiscovery.interfaces.rest.transform.FavoriteProductResourceFromEntityAssembler;
import com.bloomie.platform.productdiscovery.interfaces.rest.transform.SaveProductAsFavoriteCommandFromResourceAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.application.result.Result;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for favorite product management endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/favorite-products", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Favorite Products", description = "Product discovery favorite product management endpoints")
public class FavoriteProductController {

    private final FavoriteProductCommandService favoriteProductCommandService;
    private final FavoriteProductQueryService favoriteProductQueryService;

    public FavoriteProductController(
            FavoriteProductCommandService favoriteProductCommandService,
            FavoriteProductQueryService favoriteProductQueryService) {
        this.favoriteProductCommandService = favoriteProductCommandService;
        this.favoriteProductQueryService = favoriteProductQueryService;
    }

    /**
     * Save a product as a favorite for a user.
     *
     * @param resource the request body containing productId and userId
     * @return the saved favorite product resource
     */
    @PostMapping
    @Operation(
            summary = "Save a product as favorite",
            description = "Saves a product as a favorite for the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Favorite product saved successfully",
                    content = @Content(schema = @Schema(implementation = FavoriteProductResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict - product already saved as favorite")
    })
    public ResponseEntity<?> saveFavoriteProduct(@Valid @RequestBody SaveFavoriteProductResource resource) {
        var command = SaveProductAsFavoriteCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = favoriteProductCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                FavoriteProductResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    /**
     * Get all favorite products for a user.
     *
     * @param userId the user identifier
     * @return a list of favorite product resources
     */
    @GetMapping
    @Operation(
            summary = "Get favorite products by user",
            description = "Retrieves all products saved as favorites by the specified user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorite products retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = FavoriteProductResource.class)))
            )
    })
    public ResponseEntity<List<FavoriteProductResource>> getFavoriteProductsByUserId(
            @RequestParam
            @Parameter(description = "User unique identifier", example = "1", required = true)
            Long userId
    ) {
        var query = new GetFavoriteProductsByUserIdQuery(userId);
        var favoriteProducts = favoriteProductQueryService.handle(query);
        if (favoriteProducts.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = favoriteProducts.stream()
                .map(FavoriteProductResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    /**
     * Remove a product from a user's favorites.
     *
     * @param favoriteProductId the unique identifier of the favorite product record
     * @return 204 No Content on success, or an error response
     */
    @DeleteMapping("/{favoriteProductId}")
    @Operation(
            summary = "Remove a product from favorites",
            description = "Removes the specified favorite product record for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite product removed successfully"),
            @ApiResponse(responseCode = "404", description = "Favorite product not found")
    })
    public ResponseEntity<?> removeFavoriteProduct(
            @PathVariable
            @Parameter(description = "Favorite product unique identifier", example = "1", required = true)
            Long favoriteProductId
    ) {
        var command = new RemoveProductFromFavoritesCommand(favoriteProductId);
        var result = favoriteProductCommandService.handle(command);
        return switch (result) {
            case Result.Success<FavoriteProduct, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();
            case Result.Failure<FavoriteProduct, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}