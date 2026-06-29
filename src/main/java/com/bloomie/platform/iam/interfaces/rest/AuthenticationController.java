package com.bloomie.platform.iam.interfaces.rest;

import com.bloomie.platform.iam.application.commandservices.UserCommandService;
import com.bloomie.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterDermatologistResource;
import com.bloomie.platform.iam.interfaces.rest.resources.RegisterUserResource;
import com.bloomie.platform.iam.interfaces.rest.resources.SignInResource;
import com.bloomie.platform.iam.interfaces.rest.resources.UserResource;
import com.bloomie.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.RegisterDermatologistCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.RegisterUserCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
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

/**
 * REST controller that exposes authentication endpoints for the IAM bounded context.
 *
 * <p>Three operations are available:
 * <ul>
 *   <li>POST /api/v1/authentication/sign-in — authenticate and receive a JWT bearer token</li>
 *   <li>POST /api/v1/authentication/register — register a new Young Adult user</li>
 *   <li>POST /api/v1/authentication/register-dermatologist — register a new Dermatologist user</li>
 * </ul>
 * All three endpoints are public (no token required).
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication and user registration endpoints")
public class AuthenticationController {

    private final UserCommandService userCommandService;

    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /**
     * Authenticates a user with email and password and issues a JWT bearer token.
     *
     * @param resource sign-in request body with email and plain-text password
     * @return {@link AuthenticatedUserResource} with the issued token on success
     */
    @PostMapping("/sign-in")
    @Operation(
            summary = "User sign-in",
            description = "Authenticates a user with the provided credentials and returns a JWT bearer token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully.",
                    content = @Content(schema = @Schema(implementation = AuthenticatedUserResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials or malformed request."),
            @ApiResponse(responseCode = "404", description = "User not found with the provided email.")})
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource resource) {
        var command = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                auth -> AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(auth.getLeft(), auth.getRight()),
                HttpStatus.OK);
    }

    /**
     * Registers a new Young Adult user.
     *
     * @param resource registration request body with email, plain-text password, and name
     * @return created {@link UserResource} on success
     */
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

    /**
     * Registers a new Dermatologist user.
     *
     * @param resource registration request body with email, plain-text password, and name
     * @return created {@link UserResource} on success
     */
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
