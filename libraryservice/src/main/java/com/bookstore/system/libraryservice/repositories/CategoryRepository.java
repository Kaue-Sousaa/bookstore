package com.bookstore.system.libraryservice.repositories;

import com.bookstore.system.libraryservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByActiveTrue();

    Optional<Category> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);

    boolean existsByNameAndActiveTrueAndIdNot(String name, Long id);
}
