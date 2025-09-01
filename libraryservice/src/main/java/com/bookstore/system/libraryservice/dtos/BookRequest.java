package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Requisição para criação de um novo livro")
public record BookRequest(

        @Schema(description = "Código ISBN do livro", example = "978-3-16-148410-0")
        @Size(max = 20, message = "ISBN deve ter no máximo 20 caracteres")
        String isbn,

        @Schema(description = "Título do livro", example = "Dom Casmurro")
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 500, message = "Título deve ter no máximo 500 caracteres")
        String title,

        @Schema(description = "Subtítulo do livro", example = "Uma obra clássica da literatura brasileira")
        @Size(max = 500, message = "Subtítulo deve ter no máximo 500 caracteres")
        String subtitle,

        @Schema(description = "Descrição do livro", example = "Publicado em 1899, aborda temas como ciúme e memória.")
        String description,

        @Schema(description = "Editora responsável pela publicação", example = "Editora Globo")
        String publisher,

        @Schema(description = "Data de publicação", example = "1899-01-01")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate publishDate,

        @Schema(description = "Número de páginas", example = "256")
        @Min(value = 1, message = "Número de páginas deve ser positivo")
        Integer pages,

        @Schema(description = "Idioma do livro", example = "pt-BR")
        @Size(max = 10, message = "Idioma deve ter no máximo 10 caracteres")
        String language,

        @Schema(description = "ID da categoria do livro", example = "2")
        @NotNull(message = "Categoria é obrigatória")
        Long categoryId,

        @Schema(description = "Total de cópias cadastradas", example = "5")
        @NotNull(message = "Total de cópias é obrigatório")
        @Min(value = 1, message = "Total de cópias deve ser pelo menos 1")
        Integer totalCopies,

        @Schema(description = "Lista de IDs dos autores do livro", example = "[1, 2]")
        @NotEmpty(message = "Pelo menos um autor deve ser informado")
        List<Long> authorIds
) { }
