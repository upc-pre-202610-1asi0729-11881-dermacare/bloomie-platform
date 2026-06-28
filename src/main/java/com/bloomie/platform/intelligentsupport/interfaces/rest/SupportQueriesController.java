package com.bloomie.platform.intelligentsupport.interfaces.rest;

import com.bloomie.platform.intelligentsupport.application.commandservices.SupportQueryCommandService;
import com.bloomie.platform.intelligentsupport.application.queryservices.SupportQueryQueryService;
import com.bloomie.platform.intelligentsupport.domain.model.queries.GetSupportQueryByIdQuery;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.CreateSupportQueryResource;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.SupportQueryResource;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.UpdateSupportQueryStatusResource;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.CreateSupportQueryCommandFromResourceAssembler;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.SupportQueryResourceFromEntityAssembler;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.UpdateSupportQueryStatusCommandFromResourceAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/support-queries", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Support Queries", description = "Intelligent Support — Support Query Endpoints")
public class SupportQueriesController {

    private final SupportQueryCommandService commandService;
    private final SupportQueryQueryService supportQueryQueryService;


    public SupportQueriesController(SupportQueryCommandService commandService, SupportQueryQueryService supportQueryQueryService) {
        this.commandService = commandService;
        this.supportQueryQueryService = supportQueryQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new support query session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Support query created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data.")})
    public ResponseEntity<?> createSupportQuery(
            @Valid @RequestBody CreateSupportQueryResource resource) {
        var command = CreateSupportQueryCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupportQueryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{supportQueryId}")
    @Operation(summary = "Update the status of a support query")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Support query status updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid status value."),
            @ApiResponse(responseCode = "404", description = "Support query not found.")})
    public ResponseEntity<?> updateSupportQueryStatus(
            @PathVariable Long supportQueryId,
            @Valid @RequestBody UpdateSupportQueryStatusResource resource) {
        var command = UpdateSupportQueryStatusCommandFromResourceAssembler
                .toCommandFromResource(supportQueryId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SupportQueryResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @GetMapping("/{supportQueryId}")
    @Operation(summary = "Get a support query by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Support query found."),
            @ApiResponse(responseCode = "404", description = "Support query not found.")})
    public ResponseEntity<?> getSupportQueryById(
            @PathVariable Long supportQueryId) {
        var query = new GetSupportQueryByIdQuery(supportQueryId);
        var result = supportQueryQueryService.handle(query);
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("support-query", "intelligent.support.query.not.found"));
        }
        return ResponseEntity.ok(
                SupportQueryResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }
}