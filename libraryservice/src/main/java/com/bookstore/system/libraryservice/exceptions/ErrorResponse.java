package com.bookstore.system.libraryservice.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Estrutura de resposta para erros da API")
public record ErrorResponse(

        @Schema(description = "Momento em que o erro ocorreu")
        LocalDateTime timestamp,

        @Schema( description = "Mensagem explicativa do erro")
        String message,

        @Schema(description = "Caminho da requisição que gerou o erro")
        String path
) {
}
