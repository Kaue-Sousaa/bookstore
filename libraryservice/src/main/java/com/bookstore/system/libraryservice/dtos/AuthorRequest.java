package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "Requisição para criação de um novo autor")
public record AuthorRequest(

        @Schema(description = "Nome completo do autor", example = "Machado de Assis")
        @NotBlank(message = "Nome do autor é obrigatório")
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String name,

        @Schema(description = "Biografia do autor", example = "Considerado um dos maiores escritores brasileiros.")
        String biography,

        @Schema(description = "Data de nascimento do autor", example = "1839-06-21")
        LocalDate birthDate,

        @Schema(description = "Nacionalidade do autor", example = "Brasileiro")
        @Size(max = 100, message = "Nacionalidade deve ter no máximo 100 caracteres")
        String nationality
) {
}
