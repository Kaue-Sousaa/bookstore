package com.bookstore.system.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "RegisterUserDto",
        description = "Request object used to register a new user in the system"
)
public record RegisterUserDto(

        @Schema(
                description = "Full name of the user",
                example = "John Doe",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Schema(
                description = "Unique email address of the user",
                example = "john.doe@example.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        String email,

        @Schema(
                description = "Phone number of the user, including country code if applicable",
                example = "+1-202-555-0173",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @Schema(
                description = "Password chosen by the user",
                example = "Str0ngP@ssw0rd!",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Password cannot be blank")
        String password
) { }
