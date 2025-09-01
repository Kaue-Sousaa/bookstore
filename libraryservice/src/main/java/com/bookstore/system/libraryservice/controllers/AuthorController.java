package com.bookstore.system.libraryservice.controllers;

import com.bookstore.system.libraryservice.dtos.AuthorRequest;
import com.bookstore.system.libraryservice.dtos.AuthorResponse;
import com.bookstore.system.libraryservice.services.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "Operações relacionadas aos autores")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todos os autores", description = "Retorna uma lista de todos os autores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<AuthorResponse>> findAllAuthors(){
        return ResponseEntity.ok(authorService.findAllAuthors());
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Buscar autor por ID", description = "Retorna os detalhes de um autor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AuthorResponse> findAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar novo autor", description = "Cria um novo autor no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Nome do autor já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> createAuthor(@Valid @RequestBody AuthorRequest request){
        var author = authorService.createAuthor(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(author.id())
                        .toUri())
                .build();
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar autor", description = "Atualiza os dados de um autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "409", description = "Nome do autor já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<AuthorResponse> updateAuthor(@Valid @PathVariable Long id,
                                                       @RequestBody AuthorRequest request){
        return ResponseEntity.ok(authorService.updateAuthor(id, request));
    }

    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Remover autor", description = "Remove um autor do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Autor possui livros associados"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
