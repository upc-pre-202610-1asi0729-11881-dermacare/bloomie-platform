package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.queryservices.RoutineQueryService;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllRoutinesQuery;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.RoutineResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.RoutineResourceFromEntityAssembler;
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
 * REST controller for routine management endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/routines", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Routines", description = "Routine management routine endpoints")
public class RoutineController {

    private final RoutineQueryService routineQueryService;

    public RoutineController(RoutineQueryService routineQueryService) {
        this.routineQueryService = routineQueryService;
    }

    /**
     * Get all routines.
     *
     * @return a list of routine resources
     */
    @GetMapping
    @Operation(
            summary = "Get all routines",
            description = "Retrieves all personalized skincare routines."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Routines retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineResource.class)))
            )
    })
    public ResponseEntity<List<RoutineResource>> getAllRoutines() {
        var query = new GetAllRoutinesQuery();
        var routines = routineQueryService.handle(query);
        if (routines.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = routines.stream()
                .map(RoutineResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}