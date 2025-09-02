package com.bookstore.system.userservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "TokenDto",
        description = "Response object containing the authentication token"
)
public record TokenDto(

        @Schema(
                description = "JWT authentication token issued after a successful login or registration",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        String token
) { }
