package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Resposta com informações detalhadas de um livro")
public record BookResponse(

        Long id,

        @Schema(description = "ISBN do livro", example = "978-3-16-148410-0")
        String isbn,

        @Schema(description = "Título do livro", example = "Dom Casmurro")
        String title,

        @Schema(description = "Subtítulo do livro")
        String subtitle,

        @Schema(description = "Descrição do livro")
        String description,

        @Schema(description = "Nome da editora")
        String publisher,

        @Schema(description = "Data de publicação", example = "1899-01-01")
        LocalDate publishDate,

        @Schema(description = "Número de páginas", example = "256")
        Long pages,

        @Schema(description = "Idioma do livro", example = "pt-BR")
        String language,

        @Schema(description = "Categoria associada ao livro")
        CategoryResponse category,

        @Schema(description = "Total de cópias cadastradas", example = "5")
        Integer totalCopies,

        @Schema(description = "Cópias disponíveis para empréstimo", example = "3")
        Integer availableCopies,

        @Schema(description = "Lista de autores do livro")
        List<AuthorResponse> authors,

        @Schema(description = "Indica se o livro está ativo no sistema", example = "true")
        boolean active
) { }
