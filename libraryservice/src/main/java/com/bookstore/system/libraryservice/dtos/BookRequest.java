package com.bookstore.system.libraryservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record BookRequest(
        
        @Size(max = 20, message = "ISBN deve ter no máximo 20 caracteres")
        String isbn,

        @NotBlank(message = "Título é obrigatório")
        @Size(max = 500, message = "Título deve ter no máximo 500 caracteres")
        String title,

        @Size(max = 500, message = "Subtítulo deve ter no máximo 500 caracteres")
        String subtitle,

        String description,

        String publisher,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate publishDate,

        @Min(value = 1, message = "Número de páginas deve ser positivo")
        Integer pages,

        @Size(max = 10, message = "Idioma deve ter no máximo 10 caracteres")
        String language,

        @NotNull(message = "Categoria é obrigatória")
        Long categoryId,

        @NotNull(message = "Total de cópias é obrigatório")
        @Min(value = 1, message = "Total de cópias deve ser pelo menos 1")
        Integer totalCopies,

        @NotEmpty(message = "Pelo menos um autor deve ser informado")
        List<Long> authorIds

) {
}
