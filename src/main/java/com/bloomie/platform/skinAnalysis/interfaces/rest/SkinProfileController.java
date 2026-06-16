package com.bloomie.platform.skinAnalysis.interfaces.rest;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bloomie.platform.skinAnalysis.application.commandservices.SkinProfileCommandService;
import com.bloomie.platform.skinAnalysis.application.queryservices.SkinProfileQueryService;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByIdQuery;
import com.bloomie.platform.skinAnalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;
import com.bloomie.platform.skinAnalysis.interfaces.rest.resources.CompleteSkinProfileResource;
import com.bloomie.platform.skinAnalysis.interfaces.rest.transform.CompleteSkinProfileCommandFromResourceAssembler;
import com.bloomie.platform.skinAnalysis.interfaces.rest.transform.SkinProfileResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/skin-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Skin Profiles", description = "Skin Analysis — Skin Profile Endpoints")
public class SkinProfileController {

    private final SkinProfileCommandService commandService;
    private final SkinProfileQueryService queryService;

    public SkinProfileController(SkinProfileCommandService commandService,
                                 SkinProfileQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @PostMapping
    @Operation(summary = "Complete a patient's skin profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Skin profile completed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "409", description = "Skin profile already exists for this patient.")})
    public ResponseEntity<?> completeSkinProfile(
            @Valid @RequestBody CompleteSkinProfileResource resource) {
        var command = CompleteSkinProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result  = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                SkinProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a skin profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin profile retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin profile not found.")})
    public ResponseEntity<?> getSkinProfileById(@PathVariable Long id) {
        var result = queryService.handle(new GetSkinProfileByIdQuery(id));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin_profile", "skin_analysis.skin_profile.not_found"));
        }
        return ResponseEntity.ok(SkinProfileResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping
    @Operation(summary = "Get a skin profile by patient id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin profile retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin profile not found.")})
    public ResponseEntity<?> getSkinProfileByPatientId(@RequestParam Long patient_id) {
        var result = queryService.handle(new GetSkinProfileByPatientIdQuery(patient_id));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin_profile", "skin_analysis.skin_profile.not_found"));
        }
        return ResponseEntity.ok(SkinProfileResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }
}