package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response with basic author information")
public record AuthorResponse(

        Long id,

        @Schema(description = "Author's name", example = "Machado de Assis")
        String name,

        @Schema(description = "Author's summarized biography")
        String biography
) {
}