package com.bloomie.platform.skinanalysis.interfaces.rest;

import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import com.bloomie.platform.skinanalysis.application.commandservices.SkinProfileCommandService;
import com.bloomie.platform.skinanalysis.application.queryservices.SkinProfileQueryService;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinProfileByIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.queries.GetSkinProfileByPatientIdQuery;
import com.bloomie.platform.skinanalysis.domain.model.valueobjects.PatientId;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.CompleteSkinProfileResource;
import com.bloomie.platform.skinanalysis.interfaces.rest.resources.UpdateSkinCharacteristicsResource;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.CompleteSkinProfileCommandFromResourceAssembler;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.SkinProfileResourceFromEntityAssembler;
import com.bloomie.platform.skinanalysis.interfaces.rest.transform.UpdateSkinCharacteristicsCommandFromResourceAssembler;
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

    private static final String PROFILE_NOT_FOUND = "skin.profile.not.found";

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
            @ApiResponse(responseCode = "404", description = "Patient not found."),
            @ApiResponse(responseCode = "409", description = "Skin profile already exists for this patient.")})
    public ResponseEntity<?> completeSkinProfile(
            @Valid @RequestBody CompleteSkinProfileResource resource) {
        var command = CompleteSkinProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> SkinProfileResourceFromEntityAssembler.toResourceFromEntity(
                        queryService.handle(new GetSkinProfileByIdQuery(id)).orElseThrow()),
                HttpStatus.CREATED);
    }

    @PutMapping("/{skinProfileId}")
    @Operation(summary = "Update skin characteristics of an existing skin profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin profile updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Skin profile not found.")})
    public ResponseEntity<?> updateSkinCharacteristics(
            @PathVariable Long skinProfileId,
            @Valid @RequestBody UpdateSkinCharacteristicsResource resource) {
        var command = UpdateSkinCharacteristicsCommandFromResourceAssembler
                .toCommandFromResource(skinProfileId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                id -> SkinProfileResourceFromEntityAssembler.toResourceFromEntity(
                        queryService.handle(new GetSkinProfileByIdQuery(id)).orElseThrow()),
                HttpStatus.OK);
    }

    @GetMapping("/{skinProfileId}")
    @Operation(summary = "Get a skin profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin profile retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin profile not found.")})
    public ResponseEntity<?> getSkinProfileById(@PathVariable Long skinProfileId) {
        var result = queryService.handle(new GetSkinProfileByIdQuery(skinProfileId));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin-profile", PROFILE_NOT_FOUND));
        }
        return ResponseEntity.ok(SkinProfileResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }

    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Get a skin profile by patient id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin profile retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Skin profile not found.")})
    public ResponseEntity<?> getSkinProfileByPatientId(@PathVariable Long patientId) {
        var result = queryService.handle(
                new GetSkinProfileByPatientIdQuery(new PatientId(patientId)));
        if (result.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("skin-profile", PROFILE_NOT_FOUND));
        }
        return ResponseEntity.ok(SkinProfileResourceFromEntityAssembler.toResourceFromEntity(result.get()));
    }
}
