package com.bookstore.system.libraryservice.controllers;

import com.bookstore.system.libraryservice.dtos.AvailabilityUpdateRequest;
import com.bookstore.system.libraryservice.dtos.BookRequest;
import com.bookstore.system.libraryservice.dtos.BookResponse;
import com.bookstore.system.libraryservice.exceptions.ErrorResponse;
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
@Tag(name = "Books", description = "Operações relacionadas aos livros")
public class BookController {

    private final BookService bookService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista de todos os livros ativos", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<BookResponse>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar livro por ID", description = "Retorna os detalhes de um livro específico", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Livro encontrado",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<BookResponse> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping(value = "category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar livros por categoria", description = "Retorna todos os livros de uma categoria específica", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Lista de livros da categoria retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<List<BookResponse>> findBookByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(bookService.findBooksByCategory(categoryId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo livro", description = "Cria um novo livro no sistema", tags = {"Books"})
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Categoria ou autor não encontrado")
    @ApiResponse(responseCode = "409", description = "ISBN já existe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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
    @Operation(summary = "Atualizar livro", description = "Atualiza os dados de um livro existente", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Livro, categoria ou autor não encontrado")
    @ApiResponse(responseCode = "409", description = "ISBN já existe")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<BookResponse> updateBook(@Valid @PathVariable Long id, @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @PutMapping(value = "{id}/availability", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar disponibilidade", description = "Atualiza a disponibilidade de cópias de um livro", tags = {"Books"})
    @ApiResponse(responseCode = "200", description = "Disponibilidade atualizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = BookResponse.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @ApiResponse(responseCode = "409", description = "Cópias disponíveis não podem ser maiores que o total de cópias")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    public ResponseEntity<BookResponse> updateAvailabilityBook(@Valid @PathVariable Long id,
                                                               @RequestBody AvailabilityUpdateRequest request) {
        return ResponseEntity.ok(bookService.updateAvailability(id, request));
    }
}
