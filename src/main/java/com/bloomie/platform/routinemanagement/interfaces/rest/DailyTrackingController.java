package com.bloomie.platform.routinemanagement.interfaces.rest;

import com.bloomie.platform.routinemanagement.application.queryservices.DailyTrackingQueryService;
import com.bloomie.platform.routinemanagement.domain.model.queries.GetAllDailyTrackingsQuery;
import com.bloomie.platform.routinemanagement.interfaces.rest.resources.DailyTrackingResource;
import com.bloomie.platform.routinemanagement.interfaces.rest.transform.DailyTrackingResourceFromEntityAssembler;
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
 * REST controller for daily tracking endpoints.
 */
@RestController
@RequestMapping(value = "/api/v1/daily-trackings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Daily Trackings", description = "Routine management daily tracking endpoints")
public class DailyTrackingController {

    private final DailyTrackingQueryService dailyTrackingQueryService;

    public DailyTrackingController(DailyTrackingQueryService dailyTrackingQueryService) {
        this.dailyTrackingQueryService = dailyTrackingQueryService;
    }

    /**
     * Get all daily tracking entries.
     *
     * @return a list of daily tracking resources
     */
    @GetMapping
    @Operation(
            summary = "Get all daily trackings",
            description = "Retrieves all daily routine completion tracking records."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Daily trackings retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DailyTrackingResource.class)))
            )
    })
    public ResponseEntity<List<DailyTrackingResource>> getAllDailyTrackings() {
        var query = new GetAllDailyTrackingsQuery();
        var trackings = dailyTrackingQueryService.handle(query);
        if (trackings.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        var resources = trackings.stream()
                .map(DailyTrackingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}