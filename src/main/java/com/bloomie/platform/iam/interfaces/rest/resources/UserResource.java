package com.bloomie.platform.iam.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(name = "UserResponse", description = "User information response")
public record UserResource(
        @Schema(description = "User unique identifier", example = "1")
        Long id,

        @Schema(description = "Full name", example = "Lucía García")
        String fullName,

        @Schema(description = "Email address", example = "lucia@example.com")
        String email,

        @Schema(description = "Assigned roles", example = "[\"ROLE_YOUNG_ADULT\"]")
        List<String> roles) {}