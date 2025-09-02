package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Request for creating a new author")
public record AuthorRequest(

        @Schema(description = "Author's full name", example = "Machado de Assis")
        @NotBlank(message = "Author name is required")
        @Size(max = 255, message = "Name must have at most 255 characters")
        String name,

        @Schema(description = "Author's biography", example = "Considered one of the greatest Brazilian writers.")
        String biography,

        @Schema(description = "Author's birth date", example = "1839-06-21")
        LocalDate birthDate,

        @Schema(description = "Author's nationality", example = "Brazilian")
        @Size(max = 100, message = "Nationality must have at most 100 characters")
        String nationality
) {
}