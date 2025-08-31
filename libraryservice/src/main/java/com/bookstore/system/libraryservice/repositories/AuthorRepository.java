package com.bookstore.system.libraryservice.repositories;

import com.bookstore.system.libraryservice.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByNameContainingIgnoreCase(String name);

    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT a FROM Author a WHERE " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(a.nationality) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<Author> searchAuthors(@Param("query") String query, Pageable pageable);

    List<Author> findByNationalityContainingIgnoreCase(String nationality);

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    @Query("SELECT DISTINCT a FROM Author a JOIN a.books b WHERE b.active = true")
    List<Author> findAuthorsWithActiveBooks();

    @Query("SELECT COUNT(DISTINCT a) FROM Author a JOIN a.books b WHERE b.active = true")
    long countAuthorsWithActiveBooks();
}
