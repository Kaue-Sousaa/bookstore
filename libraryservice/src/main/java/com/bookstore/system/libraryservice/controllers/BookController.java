package com.bookstore.system.libraryservice.controllers;

import com.bookstore.system.libraryservice.dtos.AvailabilityUpdateRequest;
import com.bookstore.system.libraryservice.dtos.BookRequest;
import com.bookstore.system.libraryservice.dtos.BookResponse;
import com.bookstore.system.libraryservice.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "Operations related to books")
public class BookController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List all books", description = "Returns a list of all active books", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "List of books returned successfully",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Find book by ID", description = "Returns the details of a specific book", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Book found",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Book not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping(value = "category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List books by category", description = "Returns all books from a specific category", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "List of books from category returned successfully",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<BookResponse>> findBookByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.findBooksByCategory(categoryId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create new book", description = "Creates a new book in the system", tags = {"Books"})
    @ApiResponse(responseCode = "201", description = "Book created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "Category or author not found")
    @ApiResponse(responseCode = "409", description = "ISBN already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        var newBook = bookService.createBook(request);
        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(newBook.id())
                        .toUri())
                .body(newBook);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update book", description = "Updates the data of an existing book", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Book updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "Book, category or author not found")
    @ApiResponse(responseCode = "409", description = "ISBN already exists")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<BookResponse> updateBook(@Valid @PathVariable Long id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @PutMapping(value = "{id}/availability", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update availability", description = "Updates the availability of book copies", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Availability updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid data")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @ApiResponse(responseCode = "409", description = "Available copies cannot be greater than total copies")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<BookResponse> updateAvailabilityBook(@Valid @PathVariable Long id,
                                                               @RequestBody AvailabilityUpdateRequest request) {
        return ResponseEntity.ok(bookService.updateAvailability(id, request));
    }
}