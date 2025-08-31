package com.bookstore.system.libraryservice.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AvailabilityUpdateRequest(

        @NotNull(message = "Total de cópias é obrigatório")
        @Min(value = 0, message = "Total de cópias não pode ser negativo")
        Integer totalCopies,

        @NotNull(message = "Cópias disponíveis é obrigatório")
        @Min(value = 0, message = "Cópias disponíveis não pode ser negativo")
        Integer availableCopies
) {
}
