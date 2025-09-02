package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Request for creating a new book")
public record BookRequest(

        @Schema(description = "Book's ISBN code", example = "978-3-16-148410-0")
        @Size(max = 20, message = "ISBN must have at most 20 characters")
        String isbn,

        @Schema(description = "Book title", example = "Dom Casmurro")
        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must have at most 500 characters")
        String title,

        @Schema(description = "Book subtitle", example = "A classic work of Brazilian literature")
        @Size(max = 500, message = "Subtitle must have at most 500 characters")
        String subtitle,

        @Schema(description = "Book description", example = "Published in 1899, addresses themes such as jealousy and memory.")
        String description,

        @Schema(description = "Publisher responsible for publication", example = "Globo Publisher")
        String publisher,

        @Schema(description = "Publication date", example = "1899-01-01")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate publishDate,

        @Schema(description = "Number of pages", example = "256")
        @Min(value = 1, message = "Number of pages must be positive")
        Integer pages,

        @Schema(description = "Book language", example = "pt-BR")
        @Size(max = 10, message = "Language must have at most 10 characters")
        String language,

        @Schema(description = "Book category ID", example = "2")
        @NotNull(message = "Category is required")
        Long categoryId,

        @Schema(description = "Total number of registered copies", example = "5")
        @NotNull(message = "Total copies is required")
        @Min(value = 1, message = "Total copies must be at least 1")
        Integer totalCopies,

        @Schema(description = "List of book author IDs", example = "[1, 2]")
        @NotEmpty(message = "At least one author must be provided")
        List<Long> authorIds
) { }