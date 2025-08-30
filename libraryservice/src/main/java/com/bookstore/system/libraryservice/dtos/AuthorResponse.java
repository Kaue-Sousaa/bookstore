package com.bookstore.system.libraryservice.dtos;

public record AuthorResponse(

        Long id,
        String name,
        String biography
) {
}
