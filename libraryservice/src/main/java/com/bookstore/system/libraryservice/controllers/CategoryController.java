package com.bookstore.system.libraryservice.controllers;

import com.bookstore.system.libraryservice.dtos.CategoryRequest;
import com.bookstore.system.libraryservice.dtos.CategoryResponse;
import com.bookstore.system.libraryservice.services.CategoryService;
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
@RequestMapping("categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Operações relacionadas às categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias ativas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }


    @Operation(summary = "Buscar categoria por ID", description = "Retorna os detalhes de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Nome da categoria já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse category = categoryService.createCategory(request);

        return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/id")
                    .buildAndExpand(category.id())
                    .toUri())
                .build();
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Nome da categoria já existe"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long id,
                                                           @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping(value = "{id}")
    @Operation(summary = "Remover categoria", description = "Remove uma categoria do sistema (soft delete)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Categoria possui livros associados"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
