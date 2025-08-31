package com.bookstore.system.libraryservice.services;

import com.bookstore.system.libraryservice.dtos.AvailabilityUpdateRequest;
import com.bookstore.system.libraryservice.dtos.BookRequest;
import com.bookstore.system.libraryservice.dtos.BookResponse;
import com.bookstore.system.libraryservice.exceptions.BusinessException;
import com.bookstore.system.libraryservice.exceptions.ResourceNotFoundException;
import com.bookstore.system.libraryservice.mappers.BookMapper;
import com.bookstore.system.libraryservice.models.Book;
import com.bookstore.system.libraryservice.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final CategoryService categoryService;
    private final AuthorService authorService;

    public List<BookResponse> findAllBooks() {
        log.info("Buscando todos os livros ativos");
        return bookMapper.toResponseList(
                bookRepository.findByActiveTrue());
    }

    public BookResponse getBookById(Long id) {
        log.info("Buscando livro por ID: {}", id);
        return bookMapper.toResponse(findBookById(id));
    }

    public List<BookResponse> findBooksByCategory(Long categoryId){
        log.info("Buscando livros por categoria: {}", categoryId);

        categoryService.findCategoryById(categoryId);

        var listBooksByCategory = bookRepository.findByCategoryIdAndActiveTrue(categoryId);
        return bookMapper.toResponseList(listBooksByCategory);
    }

    @Transactional(rollbackFor = Exception.class)
    public BookResponse createBook(BookRequest request) {
        log.info("Criando novo livro: {}", request.title());

        bookRepository.findByIsbnAndActiveTrue(request.isbn())
                .ifPresent(book -> {
                    throw new BusinessException("Já existe um livro com o ISBN: " + request.isbn());
                });

        var book = bookMapper.toEntity(request);
        var authors = request.authorIds().stream()
                .map(authorService::findAuthorById)
                .toList();
        book.setCategory(categoryService.findCategoryById(request.categoryId()));
        book.setAuthors(authors);

        bookRepository.save(book);

        log.info("Livro criado com sucesso - ID: {}", book.getId());
        return bookMapper.toResponse(book);
    }

    @Transactional(rollbackFor = Exception.class)
    public BookResponse updateBook(Long id, BookRequest request) {
        log.info("Atualizando livro ID: {}", id);

        var existingBook = findBookById(id);

        bookRepository.findByIsbnAndActiveTrue(request.isbn())
                .ifPresent(book -> {
                    throw new BusinessException("Já existe um livro com o ISBN: " + request.isbn());
                });

        var category = categoryService.findCategoryById(request.categoryId());

        var authors = new ArrayList<>(request.authorIds().stream()
                .map(authorService::findAuthorById)
                .toList());

        int difference = request.totalCopies() - existingBook.getTotalCopies();
        int newAvailableCopies = Math.max(0, existingBook.getAvailableCopies() + difference);

        bookMapper.updateEntityFromRequest(request, existingBook);
        existingBook.setCategory(category);
        existingBook.setAuthors(authors);
        existingBook.setAvailableCopies(newAvailableCopies);

        bookRepository.save(existingBook);
        log.info("Livro atualizado com sucesso - ID: {}", existingBook.getId());

        return bookMapper.toResponse(existingBook);
    }

    public BookResponse updateAvailability(Long id, AvailabilityUpdateRequest request) {
        log.info("Atualizando disponibilidade do livro ID: {} - Total: {}, Disponível: {}",
                id, request.totalCopies(), request.availableCopies());

        Book book = findBookById(id);

        if (request.availableCopies() > request.totalCopies()) {
            throw new BusinessException("Cópias disponíveis não podem ser maiores que o total de cópias");
        }

        book.setTotalCopies(request.totalCopies());
        book.setAvailableCopies(request.availableCopies());

        bookRepository.save(book);
        log.info("Disponibilidade atualizada com sucesso para o livro ID: {}", id);

        return bookMapper.toResponse(book);
    }

    private Book findBookById(Long id) {
        return bookRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado com ID: " + id));
    }
}
