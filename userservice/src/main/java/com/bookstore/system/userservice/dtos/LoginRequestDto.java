package com.bookstore.system.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "LoginRequestDto",
        description = "Request object used to authenticate users in the system"
)
public record LoginRequestDto(

        @Schema(
                description = "User's registered email address",
                example = "user@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email field cannot be blank")
        String email,

        @Schema(
                description = "Password associated with the user account",
                example = "P@ssw0rd123",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password field cannot be blank")
        String password
) { }
