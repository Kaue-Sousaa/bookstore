package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request for creating a new category")
public record CategoryRequest(

        @Schema(description = "Category name", example = "Brazilian Literature")
        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Name must have at most 100 characters")
        String name,

        @Schema(description = "Category description", example = "Classic and contemporary Brazilian literature books")
        String description
) { }