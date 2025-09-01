package com.bookstore.system.libraryservice.exceptions.handler;

import com.bookstore.system.libraryservice.exceptions.BusinessException;
import com.bookstore.system.libraryservice.exceptions.ErrorResponse;
import com.bookstore.system.libraryservice.exceptions.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(
            responseCode = "400",
            description = "Erro de validação nos campos enviados",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {

        BindingResult bindingResult = ex.getBindingResult();

        ErrorResponse exceptionResponse = new ErrorResponse(
                LocalDateTime.now(),
                bindingResult.getFieldErrors().getFirst().getDefaultMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ApiResponse(
            responseCode = "404",
            description = "Recurso não encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(builderErrorResponse(ex, request));
    }

    @ExceptionHandler(BusinessException.class)
    @ApiResponse(
            responseCode = "409",
            description = "Erro de negócio",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(builderErrorResponse(ex, request));
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(
            responseCode = "500",
            description = "Erro interno inesperado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))
    )
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(builderErrorResponse(ex, request));
    }


    private static ErrorResponse builderErrorResponse(Exception ex, HttpServletRequest request) {
        return new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getRequestURI());
    }
}
