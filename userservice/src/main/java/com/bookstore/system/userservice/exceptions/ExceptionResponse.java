package com.bookstore.system.userservice.exceptions;

import java.time.LocalDateTime;

public record ExceptionResponse(LocalDateTime timestamp, String message, String details) {
}
