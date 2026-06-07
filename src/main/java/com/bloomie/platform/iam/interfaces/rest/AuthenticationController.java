package com.bloomie.platform.iam.interfaces.rest;

import com.bloomie.platform.iam.application.commandservices.UserCommandService;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterDermatologistResource;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterUserResource;
import com.bloomie.platform.iam.interfaces.rest.resources.UserResource;
import com.bloomie.platform.iam.interfaces.rest.transform.RegisterDermatologistCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.RegisterUserCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Register a new Young Adult user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully.",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "409", description = "Email already registered.")})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserResource resource) {
        var command = RegisterUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PostMapping("/register-dermatologist")
    @Operation(summary = "Register dermatologist", description = "Register a new Dermatologist user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dermatologist registered successfully.",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "409", description = "Email already registered.")})
    public ResponseEntity<?> registerDermatologist(@Valid @RequestBody RegisterDermatologistResource resource) {
        var command = RegisterDermatologistCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }
}