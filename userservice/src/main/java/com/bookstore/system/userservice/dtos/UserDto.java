package com.bookstore.system.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "UserDto",
        description = "Response object representing a user in the system"
)
public record UserDto(

        @Schema(
                description = "Unique identifier of the user",
                example = "101"
        )
        Long id,

        @Schema(
                description = "Full name of the user",
                example = "Alice Johnson"
        )
        String name,

        @Schema(
                description = "Email address of the user",
                example = "alice.johnson@example.com"
        )
        String email,

        @Schema(
                description = "Phone number of the user",
                example = "+1-303-555-0145"
        )
        String phone
) { }
