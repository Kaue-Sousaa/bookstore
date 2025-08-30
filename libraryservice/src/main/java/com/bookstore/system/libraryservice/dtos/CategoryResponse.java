package com.bookstore.system.libraryservice.dtos;

public record CategoryResponse(

        Long id,
        String name,
        String description,
        boolean active
) {
}
