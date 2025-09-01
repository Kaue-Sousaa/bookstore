package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Requisição para criação de uma nova categoria")
public record CategoryRequest(

        @Schema(description = "Nome da categoria", example = "Literatura Brasileira")
        @NotBlank(message = "Nome da categoria é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        String name,

        @Schema(description = "Descrição da categoria", example = "Livros da literatura clássica e contemporânea brasileira")
        String description
) { }
