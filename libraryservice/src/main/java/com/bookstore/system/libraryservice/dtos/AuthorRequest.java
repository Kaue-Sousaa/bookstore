package com.bookstore.system.libraryservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorRequest(

        @NotBlank(message = "Nome do autor é obrigatório")
        @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
        String name,
        String biography,
        LocalDate birthDate,

        @Size(max = 100, message = "Nacionalidade deve ter no máximo 100 caracteres")
        String nationality
) {
}
