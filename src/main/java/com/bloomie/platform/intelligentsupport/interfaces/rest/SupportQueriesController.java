package com.bloomie.platform.intelligentsupport.interfaces.rest;

import com.bloomie.platform.intelligentsupport.application.commandservices.SupportQueryCommandService;
import com.bloomie.platform.intelligentsupport.interfaces.rest.resources.CreateSupportQueryResource;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.CreateSupportQueryCommandFromResourceAssembler;
import com.bloomie.platform.intelligentsupport.interfaces.rest.transform.SupportQueryResourceFromEntityAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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

    public SupportQueriesController(SupportQueryCommandService commandService) {
        this.commandService = commandService;
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
}