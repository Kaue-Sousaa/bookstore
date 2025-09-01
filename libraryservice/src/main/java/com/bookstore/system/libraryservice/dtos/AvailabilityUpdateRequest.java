package com.bookstore.system.libraryservice.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Requisição para atualizar disponibilidade de cópias de um livro")
public record AvailabilityUpdateRequest(

        @Schema(description = "Total de cópias do livro", example = "10")
        @NotNull(message = "Total de cópias é obrigatório")
        @Min(value = 0, message = "Total de cópias não pode ser negativo")
        Integer totalCopies,

        @Schema(description = "Quantidade de cópias disponíveis", example = "7")
        @NotNull(message = "Cópias disponíveis é obrigatório")
        @Min(value = 0, message = "Cópias disponíveis não pode ser negativo")
        Integer availableCopies
) {
}
