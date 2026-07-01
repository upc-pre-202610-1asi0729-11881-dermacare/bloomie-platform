package com.bloomie.platform.iam.interfaces.rest;

import com.bloomie.platform.iam.application.commandservices.UserCommandService;
import com.bloomie.platform.iam.application.queryservices.UserQueryService;
import com.bloomie.platform.iam.domain.model.queries.GetAllUsersQuery;
import com.bloomie.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.bloomie.platform.iam.interfaces.rest.resources.ChangePasswordResource;
import com.bloomie.platform.iam.interfaces.rest.resources.UpdateUserPhotoResource;
import com.bloomie.platform.iam.interfaces.rest.resources.UpdateUserProfileResource;
import com.bloomie.platform.iam.interfaces.rest.resources.UserResource;
import com.bloomie.platform.iam.interfaces.rest.transform.ChangePasswordCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.UpdateUserPhotoCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.UpdateUserProfileCommandFromResourceAssembler;
import com.bloomie.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.bloomie.platform.shared.application.result.ApplicationError;
import com.bloomie.platform.shared.interfaces.rest.transform.ErrorResponseAssembler;
import com.bloomie.platform.shared.interfaces.rest.transform.ResponseEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Available User Endpoints")
public class UsersController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Get all users in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully.")})
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = userQueryService.handle(new GetAllUsersQuery());
        if (users.isEmpty()) return ResponseEntity.ok(Collections.emptyList());
        var resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id", description = "Get a user by their id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        var query = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(query);
        if (user.isEmpty()) {
            var error = ApplicationError.notFound("User", userId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user.get()));
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Update the profile of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<?> updateUserProfile(
            @PathVariable String userId,
            @Valid @RequestBody UpdateUserProfileResource resource) {
        var command = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                org.springframework.http.HttpStatus.OK);
    }

    @PutMapping("/{userId}/photo")
    @Operation(summary = "Update user photo", description = "Update the profile photo URL of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User photo updated successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<?> updateUserPhoto(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserPhotoResource resource) {
        var command = UpdateUserPhotoCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                UserResourceFromEntityAssembler::toResourceFromEntity,
                org.springframework.http.HttpStatus.OK);
    }

    @PutMapping("/{userId}/password")
    @Operation(summary = "Change user password", description = "Change the password of a user, verifying the current password first.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid input data."),
            @ApiResponse(responseCode = "404", description = "User not found."),
            @ApiResponse(responseCode = "422", description = "Current password is incorrect.")})
    public ResponseEntity<?> changePassword(
            @PathVariable String userId,
            @Valid @RequestBody ChangePasswordResource resource) {
        var command = ChangePasswordCommandFromResourceAssembler.toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                v -> null,
                org.springframework.http.HttpStatus.OK);
    }
}