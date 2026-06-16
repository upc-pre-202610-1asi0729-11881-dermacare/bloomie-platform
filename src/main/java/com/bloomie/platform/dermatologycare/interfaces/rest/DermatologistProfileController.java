package com.bloomie.platform.dermatologycare.interfaces.rest;

import com.bloomie.platform.dermatologycare.application.commandservices.DermatologistProfileCommandService;
import com.bloomie.platform.dermatologycare.application.queryservices.DermatologistProfileQueryService;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetAllDermatologistProfilesQuery;
import com.bloomie.platform.dermatologycare.domain.model.queries.GetDermatologistProfileByIdQuery;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.DermatologistProfileResource;
import com.bloomie.platform.dermatologycare.interfaces.rest.resources.UpdateDermatologistProfileResource;
import com.bloomie.platform.dermatologycare.interfaces.rest.transform.DermatologistProfileResourceFromEntityAssembler;
import com.bloomie.platform.dermatologycare.interfaces.rest.transform.UpdateDermatologistProfileCommandFromResourceAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/dermatologist-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Dermatologist Profiles", description = "Dermatologist Profile Endpoints")
public class DermatologistProfileController {

    private final DermatologistProfileQueryService queryService;
    private final DermatologistProfileCommandService commandService;

    public DermatologistProfileController(DermatologistProfileQueryService queryService,
                                          DermatologistProfileCommandService commandService) {
        this.queryService = queryService;
        this.commandService = commandService;
    }

    @GetMapping
    @Operation(summary = "Get all dermatologist profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully.")})
    public ResponseEntity<List<DermatologistProfileResource>> getAllProfiles() {
        var profiles = queryService.handle(new GetAllDermatologistProfilesQuery());
        if (profiles.isEmpty()) return ResponseEntity.ok(Collections.emptyList());
        var resources = profiles.stream()
                .map(DermatologistProfileResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{profileId}")
    @Operation(summary = "Get dermatologist profile by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")})
    public ResponseEntity<?> getProfileById(@PathVariable Long profileId) {
        var profile = queryService.handle(new GetDermatologistProfileByIdQuery(profileId));
        if (profile.isEmpty()) {
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(
                    ApplicationError.notFound("dermatologist-profile", profileId.toString()));
        }
        return ResponseEntity.ok(
                DermatologistProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get()));
    }

    @PutMapping("/{profileId}")
    @Operation(summary = "Update dermatologist profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "Profile not found.")})
    public ResponseEntity<?> updateProfile(
            @PathVariable Long profileId,
            @Valid @RequestBody UpdateDermatologistProfileResource resource) {
        var command = UpdateDermatologistProfileCommandFromResourceAssembler
                .toCommandFromResource(profileId, resource);
        var result = commandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                DermatologistProfileResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
