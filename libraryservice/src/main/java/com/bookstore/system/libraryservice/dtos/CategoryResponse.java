package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta com informações de uma categoria")
public record CategoryResponse(

        Long id,

        @Schema(description = "Nome da categoria", example = "Literatura Brasileira")
        String name,

        @Schema(description = "Descrição da categoria", example = "Livros da literatura clássica e contemporânea brasileira")
        String description,

        @Schema(description = "Indica se a categoria está ativa no sistema", example = "true")
        boolean active
) { }
