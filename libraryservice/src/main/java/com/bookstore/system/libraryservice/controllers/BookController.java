package com.bookstore.system.libraryservice.controllers;

import com.bookstore.system.libraryservice.dtos.AvailabilityUpdateRequest;
import com.bookstore.system.libraryservice.dtos.BookRequest;
import com.bookstore.system.libraryservice.dtos.BookResponse;
import com.bookstore.system.libraryservice.services.BookService;
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
public class BookController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponse>> findAllBooks(){
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping(value = "category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookResponse>> findBookByCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(bookService.findBooksByCategory(categoryId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request){
        var newBook = bookService.createBook(request);
        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newBook.id())
                    .toUri())
                .build();
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> updateBook(@Valid @PathVariable Long id, @RequestBody BookRequest request){
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @PutMapping(value = "{id}/availability", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookResponse> updateAvailabilityBook(@Valid @PathVariable Long id,
                                                               @RequestBody AvailabilityUpdateRequest request){
        return ResponseEntity.ok(bookService.updateAvailability(id, request));
    }
}
