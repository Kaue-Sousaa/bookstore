package com.bookstore.system.libraryservice.dtos;

import java.time.LocalDate;
import java.util.List;

public record BookResponse(
        Long id,
        String isbn,
        String title,
        String subtitle,
        String description,
        String publisher,
        LocalDate publishDate,
        Long pages,
        String language,
        CategoryResponse category,
        List<AuthorResponse> authors,
        boolean active

) {
}
