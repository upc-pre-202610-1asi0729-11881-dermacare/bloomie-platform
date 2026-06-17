package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineItemQueryService;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutineItemsQuery;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineItemResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.RoutineItemResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST controller for routine item endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/routine-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routine Items", description = "Routine management routine item endpoints")
public class RoutineItemController {

    private final RoutineItemQueryService routineItemQueryService;

    public RoutineItemController(RoutineItemQueryService routineItemQueryService) {
        this.routineItemQueryService = routineItemQueryService;
    }

    /**
     * Get all routine items.
     *
     * @return a list of routine item resources
     */
    @GetMapping
    @Operation(
            summary = "Get all routine items",
            description = "Retrieves all product steps across all skincare routines."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routine items retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineItemResource.class)))
            )
    })
    public ResponseEntity<List<RoutineItemResource>> getAllRoutineItems() {
        var query = new GetAllRoutineItemsQuery();
        var items = routineItemQueryService.handle(query);
        if (items.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = items.stream()
                .map(RoutineItemResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}