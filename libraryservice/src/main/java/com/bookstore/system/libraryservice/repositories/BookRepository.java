package com.bookstore.system.libraryservice.repositories;

import com.bookstore.system.libraryservice.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbnAndActiveTrue(String isbn);

    List<Book> findByActiveTrue();

    Optional<Book> findByIdAndActiveTrue(Long id);

    List<Book> findByCategoryIdAndActiveTrue(Long categoryId);

    @Query("""
            SELECT b FROM Book b WHERE b.active = true AND 
            (LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR 
            LOWER(b.subtitle) LIKE LOWER(CONCAT('%', :query, '%')) OR 
            LOWER(b.description) LIKE LOWER(CONCAT('%', :query, '%')) OR 
            LOWER(b.isbn) LIKE LOWER(CONCAT('%', :query, '%')) OR 
            EXISTS (SELECT 1 FROM b.authors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))))
            """)
    Page<Book> searchBooks(@Param("query") String query, Pageable pageable);
}
