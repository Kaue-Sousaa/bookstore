package com.bookstore.system.libraryservice.mappers;

import com.bookstore.system.libraryservice.dtos.BookRequest;
import com.bookstore.system.libraryservice.dtos.BookResponse;
import com.bookstore.system.libraryservice.models.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {CategoryMapper.class, AuthorMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
    @Mapping(target = "active", ignore = true)
    Book toEntity(BookRequest request);

    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "availableCopies", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(BookRequest request, @MappingTarget Book book);
}
