package com.bookstore.system.libraryservice.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String message, String path) {
}
