package com.bookstore.system.libraryservice.services;

import com.bookstore.system.libraryservice.dtos.AuthorRequest;
import com.bookstore.system.libraryservice.dtos.AuthorResponse;
import com.bookstore.system.libraryservice.mappers.AuthorMapper;
import com.bookstore.system.libraryservice.models.Author;
import com.bookstore.system.libraryservice.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorResponse> findAllAuthors() {
        log.info("Buscando todos os autores");
        return authorMapper.toResponseList(authorRepository.findAll());
    }

    public AuthorResponse getAuthorById(Long id) {
        log.info("Buscando autor por ID: {}", id);
        return authorMapper.toResponse(findAuthorById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthorResponse createAuthor(AuthorRequest request) {
        log.info("Criando novo autor: {}", request.name());

        if (authorRepository.existsByName(request.name())) {
            throw new RuntimeException("Já existe um autor com o nome: " + request.name());
        }

        var author = authorMapper.toEntity(request);
        authorRepository.save(author);

        log.info("Autor criado com sucesso - ID: {}", author.getId());
        return authorMapper.toResponse(author);
    }

    @Transactional(rollbackFor = Exception.class)
    public AuthorResponse updateAuthor(Long id, AuthorRequest request) {
        log.info("Atualizando autor ID: {}", id);

        var existingAuthor = findAuthorById(id);

        if (!request.name().equals(existingAuthor.getName()) &&
                authorRepository.existsByNameAndIdNot(request.name(), id)) {
            throw new RuntimeException("Já existe um autor com o nome: " + request.name());
        }

        authorMapper.updateEntityFromRequest(request, existingAuthor);
        authorRepository.save(existingAuthor);

        log.info("Autor atualizado com sucesso - ID: {}", existingAuthor.getId());
        return authorMapper.toResponse(existingAuthor);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteAuthor(Long id) {
        log.info("Removendo autor ID: {}", id);
        var author = findAuthorById(id);

        if (!author.getBooks().isEmpty()) {
            throw new RuntimeException("Não é possível remover autor que possui livros associados");
        }

        authorRepository.delete(author);
        log.info("Autor removido com sucesso - ID: {}", id);
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + id));
    }
}
