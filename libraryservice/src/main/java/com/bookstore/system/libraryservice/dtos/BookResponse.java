package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Response with detailed book information")
public record BookResponse(

        Long id,

        @Schema(description = "Book's ISBN", example = "978-3-16-148410-0")
        String isbn,

        @Schema(description = "Book title", example = "Dom Casmurro")
        String title,

        @Schema(description = "Book subtitle")
        String subtitle,

        @Schema(description = "Book description")
        String description,

        @Schema(description = "Publisher name")
        String publisher,

        @Schema(description = "Publication date", example = "1899-01-01")
        LocalDate publishDate,

        @Schema(description = "Number of pages", example = "256")
        Long pages,

        @Schema(description = "Book language", example = "pt-BR")
        String language,

        @Schema(description = "Category associated with the book")
        CategoryResponse category,

        @Schema(description = "Total number of registered copies", example = "5")
        Integer totalCopies,

        @Schema(description = "Available copies for loan", example = "3")
        Integer availableCopies,

        @Schema(description = "List of book authors")
        List<AuthorResponse> authors,

        @Schema(description = "Indicates whether the book is active in the system", example = "true")
        boolean active
) { }