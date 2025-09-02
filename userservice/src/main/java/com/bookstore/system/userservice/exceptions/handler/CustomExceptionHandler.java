package com.bookstore.system.userservice.exceptions.handler;

import com.bookstore.system.userservice.exceptions.BadRequestException;
import com.bookstore.system.userservice.exceptions.ExceptionResponse;
import com.bookstore.system.userservice.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))
    )
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(builderExceptionResponse(ex, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Validation error in the provided fields",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))
    )
    public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(builderExceptionResponse(ex, request), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(
            responseCode = "500",
            description = "Unexpected internal server error",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ExceptionResponse.class))
    )
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(builderExceptionResponse(ex, request));
    }

    private static ExceptionResponse builderExceptionResponse(Exception ex, WebRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}
