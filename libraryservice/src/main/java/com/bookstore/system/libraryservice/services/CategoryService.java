package com.bookstore.system.libraryservice.services;

import com.bookstore.system.libraryservice.dtos.CategoryRequest;
import com.bookstore.system.libraryservice.dtos.CategoryResponse;
import com.bookstore.system.libraryservice.exceptions.BusinessException;
import com.bookstore.system.libraryservice.exceptions.ResourceNotFoundException;
import com.bookstore.system.libraryservice.mappers.CategoryMapper;
import com.bookstore.system.libraryservice.models.Category;
import com.bookstore.system.libraryservice.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> findAllCategories() {
        log.info("Buscando todas as categorias ativas");
        return categoryMapper.toResponseList(categoryRepository.findByActiveTrue());
    }

    public CategoryResponse getCategoryById(Long id) {
        log.info("Buscando categoria por ID: {}", id);
        return categoryMapper.toResponse(findCategoryById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Criando nova categoria: {}", request.name());

        if (categoryRepository.existsByNameAndActiveTrue(request.name())) {
            throw new BusinessException("Já existe uma categoria com o nome: " + request.name());
        }

        var category = categoryMapper.toEntity(request);
        categoryRepository.save(category);

        log.info("Categoria criada com sucesso - ID: {}", category.getId());
        return categoryMapper.toResponse(category);
    }

    @Transactional(rollbackFor = Exception.class)
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        log.info("Atualizando categoria ID: {}", id);

        Category existingCategory = findCategoryById(id);

        if (!request.name().equals(existingCategory.getName()) &&
                categoryRepository.existsByNameAndActiveTrueAndIdNot(request.name(), id)) {
            throw new BusinessException("Já existe uma categoria com o nome: " + request.name());
        }

        categoryMapper.updateEntityFromRequest(request, existingCategory);
        categoryRepository.save(existingCategory);

        log.info("Categoria atualizada com sucesso - ID: {}", existingCategory.getId());
        return categoryMapper.toResponse(existingCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(Long id) {
        log.info("Removendo categoria ID: {}", id);

        Category category = findCategoryById(id);

        if (!category.getBooks().isEmpty()) {
            throw new BusinessException("Não é possível remover categoria que possui livros associados");
        }

        category.setActive(false);
        categoryRepository.save(category);

        log.info("Categoria removida com sucesso - ID: {}", id);
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }
}
