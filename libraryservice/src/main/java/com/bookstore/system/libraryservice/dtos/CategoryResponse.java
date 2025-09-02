package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response with category information")
public record CategoryResponse(

        Long id,

        @Schema(description = "Category name", example = "Brazilian Literature")
        String name,

        @Schema(description = "Category description", example = "Classic and contemporary Brazilian literature books")
        String description,

        @Schema(description = "Indicates whether the category is active in the system", example = "true")
        boolean active
) { }