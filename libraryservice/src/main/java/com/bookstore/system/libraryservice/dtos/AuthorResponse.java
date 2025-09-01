package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com informações básicas de um autor")
public record AuthorResponse(

        Long id,

        @Schema(description = "Nome do autor", example = "Machado de Assis")
        String name,

        @Schema(description = "Biografia resumida do autor")
        String biography
) {
}
